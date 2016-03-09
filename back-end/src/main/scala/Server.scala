
import grocery.common._
import scala.concurrent.Future
import akka.http.scaladsl._
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl._
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import scala.util.control.NonFatal
import akka.event.Logging
import upickle.default._
import akka.http.scaladsl.model.ws.Message
import akka.stream.OverflowStrategy

//NOTE server extends app, which is the way of saying it is "runnable" and has a main method
object Server extends App {
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  implicit val ec = actorSystem.dispatcher //Needed for futures.
  val log = Logging(actorSystem, getClass)
  val staticContentDir = "/home/rleibman/sacjug/grocery/front-end"

  //NOTE This is definitely no the way I'd do this in prod, just trying to simplify the example somewhere
  //If there's time
  trait dao {
    def allGroceryItems: Future[Seq[GroceryItem]]
    def add(item: GroceryItem): Future[GroceryItem]
  }

  object fakedao extends dao {
    private var aisles = Set(Aisle("Canned Items"))
    private var groceryItems = List(
      GroceryItem("Peanuts", aisles.head))

    override def allGroceryItems = Future.successful(groceryItems)
    override def add(item: GroceryItem) = Future.successful {
      groceryItems = item :: groceryItems
      item
    }
  }

  //NOTE ignore this for now, it's for websockets if we want to play with them
  def processStatusUpdateFlow: Flow[Message, Message, Any] = {
    val in = Flow[Message]
      .map(msg ⇒ {
        log.debug(msg.toString())
        msg
      })
      .to(Sink.ignore)

    val out =
      Source.actorRef[Message](1, OverflowStrategy.fail)
        .mapMaterializedValue(a ⇒ {
          log.debug(s"new User ${a}")
          a
        })

    Flow.fromSinkAndSource(in, out)
  }

  val route = handleWebSocketMessages {
    processStatusUpdateFlow
  } ~
    path("groceryItems") {
      get {
        complete {
          //NOTE explain for comprehension of futures (because they have flat and flatMap)
          val fut = for {
            items ← fakedao.allGroceryItems
          } yield (write(items))
          fut
        }
      } ~ put {
        entity(as[String]) { str ⇒
          complete {
            //NOTE I have not tested, in production I haven't used pickle
            val item = read[GroceryItem](str)
            for {
              updated ← fakedao.add(item)
            } yield (write(updated))
          }
        }
      }
    } ~
    pathEndOrSingleSlash {
      //NOTE this is the "welcome document"
      log.debug(s"asked for root, replying with ${staticContentDir}/index.html")
      getFromFile(s"${staticContentDir}/index.html")
    } ~
    {
      //NOTE this is everything else that doesn't match the route, in other systems this would be where I capture
      //that it fell through and answer a 404 (or just remove this path)
      //But here I return my scala-js app.
      log.debug(s"I assumed you asked for static content, replying with something from ${staticContentDir}")
      encodeResponse { getFromDirectory(staticContentDir) }
    }

  //NOTE note how easy it is to bind to an http port,
  // You could create the beginnings of an ldap server by
  // IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", "993"))
  val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] = {
    Http().bind(interface = "localhost", port = 8080)
  }

  //NOTE explain streams
  //This uses streams!
  val bindingFuture: Future[Http.ServerBinding] =
    serverSource.to(Sink.foreach { connection ⇒ // foreach materializes the source
      log.debug("Accepted new connection from " + connection.remoteAddress)
      // ... and then actually handle the connection
      try connection.flow.joinMat(route)(Keep.both).run()
      catch {
        case NonFatal(e) ⇒
          log.error(e, "Could not materialize handling flow for {}", connection)
          throw e
      }
    }).run()
}
