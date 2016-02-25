package com.scalaReplOl.service

import java.io._
import java.util.Properties
import org.slf4j.LoggerFactory
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter._

class ScalaIntp {
  val logger =  LoggerFactory.getLogger(getClass)

  private val properties = new Properties()
  properties.load(new FileReader("conf/conf.properties"))
  private val scalaLibraryPath = properties.getProperty("scalaLibraryPath")
  private val progredePath = properties.getProperty("progredePath")

  private var settings = new Settings
  settings.bootclasspath.append(scalaLibraryPath)
  settings.bootclasspath.append(progredePath)
  settings.classpath.append(scalaLibraryPath)
  settings.classpath.append(progredePath)
  println(settings.classpath)
  settings.Yreplsync.value = true

  //use when launching normally outside SBT
  settings.usejavacp.value = true

  //set up outputStream to catch output result
  var byteArrayOutputStream = new ByteArrayOutputStream()
  var printWriter = new JPrintWriter(byteArrayOutputStream)

  var intp = new IMain(settings, printWriter)
  setSecurityPolicy()

  //set security Policy to deny file operations
  private def setSecurityPolicy() = {
    logger.info("set security policy")
    try{
      logger.info(exec("System.setProperty(\"java.security.policy\",\"=conf/prograde.policy\")"))
      logger.info(exec("import net.sourceforge.prograde.sm.ProGradeJSM"))
      logger.info(exec("System.setSecurityManager(new ProGradeJSM)"))
    }catch{
      case ex: Exception => logger.error(ex.toString)
    }
  }

  def exec(cmd: String): String = {
    try{
    byteArrayOutputStream.reset()
    intp.interpret(cmd)
    byteArrayOutputStream.toString
    }catch {
      case ex: Exception => ex.toString
    }
  }

  def reset(): Unit = {
    intp.reset()
  }

  def close() = {
    intp.close()
  }
}

object test{
  def main(args: Array[String]) {
    val intp = new ScalaIntp

    println(intp.exec("1+1"))
    println(intp.exec("import java.io.FileWriter"))
    println(intp.exec("val f = new FileWriter(\"sdfd\")"))

  }
}