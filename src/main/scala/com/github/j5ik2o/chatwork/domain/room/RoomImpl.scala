package com.github.j5ik2o.chatwork.domain.room

import java.util.Date
import com.github.j5ik2o.chatwork.domain.contact.AccountId
import java.net.URL

private[room]
class RoomImpl
(val identity: RoomId,
 val name: String,
 val roomType: RoomType.Value,
 val roomRole: RoomRole.Value,
 val sticky: Boolean,
 val description: Option[String],
 val unreadNum: Int,
 val mentionNum: Int,
 val myTaskNum: Int,
 val messageNum: Int,
 val fileNum: Int,
 val taskNum: Int,
 val iconPath: String,
 val lastUpdateTime: Option[Date],
 val members: Seq[Member])
  extends Room {



}

class MemberImpl
(val identity: AccountId,
 val roomId: RoomId,
 val name: String,
 val chatWorkId: String,
 val organizationId: Int,
 val organizationName: String,
 val department: String,
 val avatarImageUrl: URL,
 val role: String)
  extends Member {
}