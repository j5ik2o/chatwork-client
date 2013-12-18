package com.github.j5ik2o.chatwork.domain.account

import com.github.j5ik2o.chatwork.domain.RoomId
import java.net.URL
import org.sisioh.dddbase.core.model.{EntityCloneable, Entity}

trait Account extends Entity[AccountId]  {

  // val accountId: AccountId = identity

  val roomId: RoomId

  val name: String

  val chatWorkId: String

  val organizationId: Int

  val organizationName: String

  val department: String

  val avatarImageUrl: URL
}
