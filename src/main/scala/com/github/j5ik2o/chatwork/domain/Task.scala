package com.github.j5ik2o.chatwork.domain

import java.util.Date
import org.sisioh.dddbase.core.model.Entity
import com.github.j5ik2o.chatwork.domain.account.AccountId

/**
 * {
    "task_id": 3,
    "room": {
      "room_id": 5,
      "name": "Group Chat Name",
      "icon_path": "https://example.com/ico_group.png"
    },
    "assigned_by_account": {
      "account_id": 456,
      "name": "Anna",
      "avatar_image_url": "https://example.com/def.png"
    },
    "message_id": 13,
    "body": "buy milk",
    "limit_time": 1384354799,
    "status": "open"
  }
 */
trait Task extends Entity[TaskId] {
  val roomId: RoomId
  val accountId: AccountId
  val messageId: MessageId
  val body: String
  val limitTime: Date
  val status: StatusType.Value
}
