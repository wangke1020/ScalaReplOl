package com.scalaReplOl.service


import java.io._
import java.util.Properties
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter._



class ScalaIntp {

  private val properties = new Properties()
  properties.load(new FileReader("conf/conf.properties"))
  private val scalaLibraryPath = properties.getProperty("scalaLibraryPath")

  private var settings = new Settings
  settings.bootclasspath.append(scalaLibraryPath)
  settings.classpath.append(scalaLibraryPath)
  settings.Yreplsync.value = true

  //use when launching normally outside SBT
  settings.usejavacp.value = true

  //set up outputStream to catch output result
  var byteArrayOutputStream = new ByteArrayOutputStream()
  var printWriter = new JPrintWriter(byteArrayOutputStream)


  var intp = new IMain(settings, printWriter)

  def exec(cmd: String): String = {
    byteArrayOutputStream.reset()
    var x = intp.interpret(cmd)
    byteArrayOutputStream.toString
  }
  def reset(): Unit = {
    intp.reset()
  }
  def close() = {
    intp.close()
  }
}