
import scala.scalajs.js.JSApp
import org.scalajs.dom
import dom.document
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._
import chandu0101.scalajs.react.components.ReactTable
import scalacss.mutable.GlobalRegistry
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry
import scalacss.Defaults._
import org.scalajs.dom.raw._

//NOTE the beginning of any scala-js app is an object which extends JSApp
object Grocery extends JSApp {

  object AppCSS {
    def load = {
      GlobalRegistry.register(ReactTable.DefaultStyle)
      GlobalRegistry.onRegistration(_.addNOTEcument())
    }
  }
  //This is required, but not used
  def main(): Unit = {
  }

  type MyType = Seq[Option[String]]

  AppCSS.load
  val HelloMessage = ReactComponentB[String]("HelloMessage")
    //NOTE show here how easy it is to put in some extra stuff, add a title for instance
    .render($ ⇒ <.div("Hello ", $.props))
    .build

  //  ReactDOM.render(HelloMessage("Roberto"), dom.document.getElementById("content"))
  ReactDOM.render(GroceryTable()(), dom.document.getElementById("content"))

  /////////////////////////////////////////////////////
  // NOTE Websocket stuff
  val processUpdateSocket = new WebSocket(s"ws://localhost:8080/websocket")
  processUpdateSocket.onopen = {
    (event: Event) ⇒
      println(s"Websocket: connection to the server was successful: ${event}")
  }
  processUpdateSocket.onclose = {
    (event: CloseEvent) ⇒
      println(s"Websocket: We got an OnClose event, that's odd, probably the server restarted, we should probably try to reconnect ${event.code}-${event.reason}")
  }
  processUpdateSocket.onerror = {
    (event: ErrorEvent) ⇒
      println(s"Websocket: Error happened ${event}")
  }
  processUpdateSocket.onmessage = {
    (event: MessageEvent) ⇒
      println(s"Websocket: We got a message! ${event}")
  }

}
