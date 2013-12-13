package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.json4s.JValue

/**
 * "account_id": 123,
    "role": "member",
    "name": "John Smith",
    "chatwork_id": "tarochatworkid",
    "organization_id": 101,
    "organization_name": "Hello Company",
    "department": "Marketing",
    "avatar_image_url": "https://example.com/abc.png"
 */
case class Member
(acountId: Int,
 role: String,
 name: String,
 chatWorkId: String,
 organizationId: Int,
 organizationName: String,
 department: String,
 avatarImageUrl: String)

object Member {

  def apply(jValue: JValue): Member = {
    Member(
      (jValue \ "account_id").as[Int],
      (jValue \ "role").as[String],
      (jValue \ "name").as[String],
      (jValue \ "chatwork_id").as[String],
      (jValue \ "organization_id").as[Int],
      (jValue \ "organization_name").as[String],
      (jValue \ "department").as[String],
      (jValue \ "avatar_image_url").as[String]
    )
  }


}
