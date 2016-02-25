package com.scalaReplOl.app

import com.scalaReplOl.service.IntpPool
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._
import org.scalatra.swagger._
import org.slf4j.LoggerFactory

class ReplRestServlet(implicit val swagger: Swagger)
  extends ScalatraServlet with NativeJsonSupport with SwaggerSupport {

  val logger =  LoggerFactory.getLogger(getClass)

  protected implicit val jsonFormats: Formats = DefaultFormats

  // A description of our application. This will show up in the Swagger docs.
  protected val applicationDescription = ""

  private var intpPool = new IntpPool

  logger.info("start repl rest service")

  before() {
    contentType = formats("json")
  }

  def getCurMillisStr: String = {
    System.currentTimeMillis().toString
  }
  def packMsg(msg: String) = {
    Msg(msg, getCurMillisStr)
  }


  //REST API
  //Only allow localhost to access the rest API
  get("/register"){
      packMsg(intpPool.registerNew())
  }

  get("/execute"){

    val uuid = params.get("uuid").get
    val cmd = params.get("cmd").get
    logger.info("execute(uuid: %s, cmd: %s)".format(uuid, cmd))
    packMsg(intpPool.exec(uuid.toString, cmd))
  }

  get("/logout") {
    //
    params.get("uuid") match {
      case Some(uuid: String) =>
        logger.info("user with uid: %s logout".format(uuid))
        packMsg(intpPool.logout(uuid).toString)
      case None => packMsg("false")
    }
  }
}
