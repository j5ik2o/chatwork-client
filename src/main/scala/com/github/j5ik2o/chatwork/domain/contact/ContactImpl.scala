package com.github.j5ik2o.chatwork.domain.contact

import com.github.j5ik2o.chatwork.domain.RoomId
import java.net.URL

class ContactImpl
(val identity: AccountId,
 val roomId: RoomId,
 val name: String,
 val chatWorkId: String,
 val organizationId: Int,
 val organizationName: String,
 val department: String,
 val avatarImageUrl: URL
  ) extends Contact {

  def compare(that: Contact): Int =
    this.identity.value compare that.identity.value

}
