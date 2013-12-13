package com.github.j5ik2o.chatwork.infrastructure.api.my

import org.json4s._
import org.json4s.DefaultReaders._

case class Room
(roomId: Int, name: String, iconPath: String)

object Room {

  def apply(jValue: JValue): Room = {
    Room(
      (jValue \ "room_id").as[Int],
      (jValue \ "name").as[String],
      (jValue \ "icon_path").as[String]
    )
  }

}

case class Account
(accountId: Int, name: String, avatarImageUrl: String)

object Account {

  def apply(jValue: JValue): Account = {
    Account(
      (jValue \ "account_id").as[Int],
      (jValue \ "name").as[String],
      (jValue \ "avatar_image_url").as[String]
    )
  }

}

case class Task
(taskId: Int,
 room: Room,
 assignByAccount: Account,
 messageId: Int,
 body: String,
 limitTime: Long,
 status: String)

object Task {

  def apply(jValue: JValue): Task = {
    Task(
      taskId = (jValue \ "task_id").as[Int],
      room = Room(jValue \ "room"),
      assignByAccount = Account(jValue \ "assign_by_account"),
      messageId = (jValue \ "messageId").as[Int],
      body = (jValue \ "body").as[String],
      limitTime = (jValue \ "limit_time").as[Long],
      status = (jValue \ "status").as[String]
    )
  }
}
