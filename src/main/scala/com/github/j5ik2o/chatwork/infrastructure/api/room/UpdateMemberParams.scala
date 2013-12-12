package com.github.j5ik2o.chatwork.infrastructure.api.room

import com.github.j5ik2o.chatwork.infrastructure.api.RequestParams

case class UpdateMemberParams
(roomId: Int,
 membersAdminIds: Seq[Int],
 membersMemberIds: Seq[Int],
 membersReadonlyIds: Seq[Int]) extends RequestParams {

  def toMap: Map[String, Any] = {
    Map("members_admin_ids" -> membersAdminIds.mkString(",")) ++
      toMap("members_member_ids", membersMemberIds) ++
      toMap("members_readonly_ids", membersReadonlyIds)
  }

}
