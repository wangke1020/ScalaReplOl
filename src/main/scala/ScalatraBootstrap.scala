import com.scalaReplOl.app._
import org.scalatra._
import javax.servlet.ServletContext

import org.scalatra.swagger.{ApiInfo, Swagger}

class ScalatraBootstrap extends LifeCycle {

  val info = ApiInfo(
      "The scalaRepl API",
      "Docs for the scalaRepl API",
      "http://scalatra.org",
      "apiteam@scalatra.org",
      "MIT",
      "http://opensource.org/licenses/MIT")
  implicit val swagger = new Swagger(Swagger.SpecVersion, "1", info)

  override def init(context: ServletContext) {
    context.mount(new ReplRestServlet, "/rest")
    context.mount(new MainServlet, "/")
  }
}
