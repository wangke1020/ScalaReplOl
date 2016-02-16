package com.scalaReplOl.app

import java.io.File

import org.scalatra._
import scalate.ScalateSupport

class MainServlet extends ScalatraServlet with ScalateSupport {

  get("/"){
    contentType="text/html"
    new File(servletContext.getResource("/index.html").getFile)
  }
}