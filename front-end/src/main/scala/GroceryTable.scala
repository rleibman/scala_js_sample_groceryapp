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
import scala.concurrent.Future
import scala.language.implicitConversions
import org.scalajs.dom.ext.Ajax
import upickle.default._
import grocery.common.GroceryItem

//NOTE example scala-js React component
object GroceryTable {
  import scala.concurrent.ExecutionContext.Implicits.global
  case class State(groceryItems: Seq[GroceryItem] = List())

  implicit def futToCallback(fut: Future[Callback]): Callback = Callback(fut.foreach(_.runNow()))

  case class Backend($: BackendScope[Unit, State]) {
    def refresh() = {
      Ajax.get(s"http://localhost:8080/groceryItems").map { xhr ⇒
        try {
          val groceryItems = read[Seq[GroceryItem]](xhr.responseText)
          $.modState(_.copy(groceryItems = groceryItems))
        } catch {
          case e: upickle.Invalid.Data ⇒
            dom.console.error(e.msg + ":" + e.data)
            throw e
        }
      }
    }
    //NOTE This is the render table
    def render(state: State) = {
      val rows = state.groceryItems.map(item ⇒ Map("name" -> item.name, "qty" -> s"${item.qty} ${item.units}"))
      ReactTable(
        data = rows.toVector,
        columns = List("name", "qty"),
        rowsPerPage = 6,
        enableSearch = false)
    }
  }

  val component = ReactComponentB[Unit]("Table")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount($ ⇒ $.backend.refresh())
    .buildU

  def apply() = component

}
