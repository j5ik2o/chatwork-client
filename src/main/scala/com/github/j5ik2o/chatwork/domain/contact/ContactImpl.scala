package com.github.j5ik2o.chatwork.domain.contact

import java.net.URL
import com.github.j5ik2o.chatwork.domain.room.RoomId
import com.github.j5ik2o.chatwork.domain.{Organization, AccountId}

class ContactImpl
(val identity: AccountId,
 val roomId: RoomId,
 val name: String,
 val chatWorkId: String,
 val organization: Organization,
 val department: String,
 val avatarImageUrl: URL
  ) extends Contact {

  def compare(that: Contact): Int =
    this.identity.value compare that.identity.value

}
