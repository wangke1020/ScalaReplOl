package com.scalaReplOl.service

import collection.mutable.Map
import java.util.UUID
class IntpPool {

  private var pool  = Map[String, ScalaIntp]()

  def registerNew(): String = {
    val uuid = UUID.randomUUID().toString
    val intp = new ScalaIntp
    pool.put(uuid, intp)
    uuid
  }

  def exec(uuid: String, cmd: String):String = {
    pool.get(uuid) match {
      case Some(intp) => intp.exec(cmd)
      case _ => ""
    }
  }

  def logout(uuid : String): Boolean = {
    pool.get(uuid) match {
      case Some(intp) =>
        intp.close()
        pool.remove(uuid)
        true
      case _ => false
    }
  }
}
