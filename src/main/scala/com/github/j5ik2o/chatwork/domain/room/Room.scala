/*
 * Copyright 2013 Junichi Kato. (http://j5ik2o.me/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.github.j5ik2o.chatwork.domain.room

import java.util.Date
import org.sisioh.dddbase.core.model.Entity
import com.github.j5ik2o.chatwork.domain.contact.{AccountId, Contact}
import java.net.URL

trait Room extends Entity[RoomId] {

  val name: String

  val roomType: RoomType.Value

  val roomRole: RoomRole.Value

  val sticky: Boolean

  val description: Option[String]

  val unreadNum: Int

  val mentionNum: Int

  val myTaskNum: Int

  val messageNum: Int

  val fileNum: Int

  val taskNum: Int

  val iconPath: String

  val lastUpdateTime: Option[Date]

  val members: Seq[Member]



}

/**
 * {
    "account_id": 123,
    "role": "member",
    "name": "John Smith",
    "chatwork_id": "tarochatworkid",
    "organization_id": 101,
    "organization_name": "Hello Company",
    "department": "Marketing",
    "avatar_image_url": "https://example.com/abc.png"
  }
 */
trait Member extends Contact {
  val role: String
}

object Room {

  object Member {
    def apply
    (identity: AccountId,
     roomId: RoomId,
     name: String,
     chatWorkId: String,
     organizationId: Int,
     organizationName: String,
     department: String,
     avatarImageUrl: URL,
     role: String): Member = {
      new MemberImpl(
        identity,
        roomId,
        name,
        chatWorkId,
        organizationId,
        organizationName,
        department,
        avatarImageUrl,
        role
      )
    }
  }

  def apply(identity: RoomId,
            name: String,
            roomType: RoomType.Value,
            roomRole: RoomRole.Value,
            sticky: Boolean,
            description: Option[String],
            unreadNum: Int,
            mentionNum: Int,
            myTaskNum: Int,
            messageNum: Int,
            fileNum: Int,
            taskNum: Int,
            iconPath: String,
            lastUpdateTime: Option[Date],
            members: Seq[Member]): Room =
    new RoomImpl(
      identity,
      name,
      roomType,
      roomRole,
      sticky,
      description,
      unreadNum,
      mentionNum,
      myTaskNum,
      messageNum,
      fileNum,
      taskNum,
      iconPath,
      lastUpdateTime,
      members
    )
}