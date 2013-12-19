package com.github.j5ik2o.chatwork.domain.contact

import java.net.URL
import org.sisioh.dddbase.core.model.{EntityCloneable, Entity}
import com.github.j5ik2o.chatwork.domain.room.RoomId
import com.github.j5ik2o.chatwork.domain.{Organization, AccountId}

trait Contact extends Entity[AccountId]  {

  val roomId: RoomId

  val name: String

  val chatWorkId: String

  val organization: Organization

  val department: String

  val avatarImageUrl: URL

  override def toString() = {
    Seq(
      s"identity = $identity",
      s"roomdId = $roomId",
      s"name = $name",
      s"chatWorkId = $chatWorkId",
      s"organizationId = $organization",
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
   organization: Organization,
   department: String,
   avatarImageUrl: URL
    ): Contact = {
    new ContactImpl(
      identity,
      roomId,
      name,
      chatWorkId,
      organization,
      department,
      avatarImageUrl
    )
  }


}
