package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.Option
import com.github.j5ik2o.chatwork.infrastructure.api.{RequestParams, IconPresets}

case class CreateRoomParams
(name: String,
 membersAdminIds: Seq[Int],
 iconPreset: Option[IconPresets.Value] = None,
 description: Option[String] = None,
 membersMemberIds: Seq[Int] = Seq.empty,
 membersReadonlyIds: Seq[Int] = Seq.empty) extends RequestParams {
  def toMap: Map[String, Any] = {
    Map(
      "name" -> name,
      "members_admin_ids" -> membersAdminIds.mkString(",")
    ) ++
      toMap("icon_preset", iconPreset) ++
      toMap("members_member_ids", membersMemberIds) ++
      toMap("members_readonly_ids", membersReadonlyIds) ++
      toMap("description", description)
  }
}
