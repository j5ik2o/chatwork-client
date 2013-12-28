package com.github.j5ik2o.chatwork.domain.room

import com.github.j5ik2o.chatwork.domain.AccountId
import com.github.j5ik2o.chatwork.domain.room.RoomId
import java.net.URL
import java.util.Date
import org.sisioh.dddbase.core.model.Entity

/**
 * {
  "message_id": 5,
  "account": {
    "account_id": 123,
    "name": "Bob",
    "avatar_image_url": "https://example.com/ico_avatar.png"
  },
  "body": "Hello Chatwork!",
  "send_time": 1384242850,
  "update_time": 0
}
 */
trait Message extends Entity[MessageId] {
  val roomId: RoomId
  val senderId: AccountId
  val senderName: String
  val senderAvatarImageUrl: URL
  val body: String
  val sendTime: Date
  val updateTime: Date
}

