package com.github.j5ik2o.chatwork.domain.account

import com.github.j5ik2o.chatwork.domain.RoomId
import java.net.URL

class AccountImpl
(val identity: AccountId,
 val roomId: RoomId,
 val name: String,
 val chatWorkId: String,
 val organizationId: Int,
 val organizationName: String,
 val department: String,
 val avatarImageUrl: URL
  ) extends Account {

  def compare(that: Account): Int =
    this.identity.value compare that.identity.value

}
