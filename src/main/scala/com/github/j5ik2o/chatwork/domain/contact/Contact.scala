package com.github.j5ik2o.chatwork.domain.contact

import java.net.URL
import org.sisioh.dddbase.core.model.{EntityCloneable, Entity}
import com.github.j5ik2o.chatwork.domain.room.RoomId

trait Contact extends Entity[AccountId]  {

  // val accountId: AccountId = identity

  val roomId: RoomId

  val name: String

  val chatWorkId: String

  val organizationId: Int

  val organizationName: String

  val department: String

  val avatarImageUrl: URL

  override def toString() = {
    Seq(
      s"identity = $identity",
      s"roomdId = $roomId",
      s"name = $name",
      s"chatWorkId = $chatWorkId",
      s"organizationId = $organizationId",
      s"organizationName = $organizationName",
      s"department = $department",
      s"avatarImageUrl = $avatarImageUrl"
    ).mkString(", ")
  }

}

object Contact {
  def apply
  (identity: AccountId,
   roomId: RoomId,
   name: String,
   chatWorkId: String,
   organizationId: Int,
   organizationName: String,
   department: String,
   avatarImageUrl: URL
    ): Contact = {
    new ContactImpl(
      identity,
      roomId,
      name,
      chatWorkId,
      organizationId,
      organizationName,
      department,
      avatarImageUrl
    )
  }


}
