package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.json4s._

case class UpdateResult(admin: Seq[Int], member: Seq[Int], readonly: Seq[Int])

object UpdateResult {

  def apply(jValue: JValue): UpdateResult = {
    UpdateResult(
      (jValue \ "admin").as[JArray].arr.map(e => e.as[Int]).toSeq,
      (jValue \ "member").as[JArray].arr.map(e => e.as[Int]).toSeq,
      (jValue \ "readonly").as[JArray].arr.map(e => e.as[Int]).toSeq)
    )
  }

}
