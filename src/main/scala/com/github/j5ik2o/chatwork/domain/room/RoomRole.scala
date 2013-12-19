package com.github.j5ik2o.chatwork.domain.room

object RoomRole extends Enumeration {

  val Admin = Value("admin")
  val Member = Value("member")
  val ReadOnly = Value("readonly")

}
