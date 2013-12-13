package com.github.j5ik2o.chatwork.infrastructure.api.my

import org.json4s._
import org.json4s.DefaultReaders._

/**
 * Created by junichi on 2013/12/14.
 */
/**
 * {
  "unread_room_num": 2,
  "mention_room_num": 1,
  "mytask_room_num": 3,
  "unread_num": 12,
  "mention_num": 1,
  "mytask_num": 8
}
 */
case class Status
(unreadRoomNum: Int,
 mentionRoomNum: Int,
 mytaskRoomNum: Int,
 ureadNum: Int,
 mentionNum: Int,
 mytaskNum: Int)

object Status {

  def apply(jValue: JValue): Status = {
    Status(
      (jValue \ "unread_room_num").as[Int],
      (jValue \ "mention_room_num").as[Int],
      (jValue \ "mytask_room_num").as[Int],
      (jValue \ "unread_num").as[Int],
      (jValue \ "mention_num").as[Int],
      (jValue \ "mytask_num").as[Int]
    )
  }
}
