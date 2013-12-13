package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.json4s._
import org.json4s.DefaultReaders._

case class Room
(roomId: Int,
 name: String,
 roomType: String,
 roomRole: String,
 sticky: Boolean,
 unreadNum: Int,
 mentionNum: Int,
 mytaskNum: Int,
 messageNum: Int,
 fileNum: Int,
 taskNum: Int,
 iconPath: String,
 description: Option[String] = None,
 lastUpdateTime: Option[Long] = None)

object Room {

  def single(jValue: JValue): Room = {
    Room(
      (jValue \ "room_id").as[Int],
      (jValue \ "name").as[String],
      (jValue \ "type").as[String],
      (jValue \ "role").as[String],
      (jValue \ "sticky").as[Boolean],
      (jValue \ "unread_num").as[Int],
      (jValue \ "mention_num").as[Int],
      (jValue \ "mytask_num").as[Int],
      (jValue \ "message_num").as[Int],
      (jValue \ "file_num").as[Int],
      (jValue \ "task_num").as[Int],
      (jValue \ "icon_path").as[String],
      Some((jValue \ "description").as[String])
    )
  }

  def listEntry(jValue: JValue): Room = {
    Room(
      (jValue \ "room_id").as[Int],
      (jValue \ "name").as[String],
      (jValue \ "type").as[String],
      (jValue \ "role").as[String],
      (jValue \ "sticky").as[Boolean],
      (jValue \ "unread_num").as[Int],
      (jValue \ "mention_num").as[Int],
      (jValue \ "mytask_num").as[Int],
      (jValue \ "message_num").as[Int],
      (jValue \ "file_num").as[Int],
      (jValue \ "task_num").as[Int],
      (jValue \ "icon_path").as[String],
      None,
      Some((jValue \ "last_update_time").as[Long]
      ))
  }

}
