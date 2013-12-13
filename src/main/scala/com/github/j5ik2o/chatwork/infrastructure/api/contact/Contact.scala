package com.github.j5ik2o.chatwork.infrastructure.api.contact

import org.json4s._
import org.json4s.DefaultReaders._

/**
 * {
    "account_id": 123,
    "room_id": 322,
    "name": "John Smith",
    "chatwork_id": "tarochatworkid",
    "organization_id": 101,
    "organization_name": "Hello Company",
    "department": "Marketing",
    "avatar_image_url": "https://example.com/abc.png"
  }
 */
case class Contact
(accountId: Int,
 roomId: Int,
 name: String,
 chatWorkId: String,
 organizationId: Int,
 organizationName: String,
 department: String,
 avatarImageUrl: String)

object Contact {

  def apply(jValue: JValue): Contact = {
    Contact(
      (jValue \ "account_id").as[Int],
      (jValue \ "room_id").as[Int],
      (jValue \ "name").as[String],
      (jValue \ "chatwork_id").as[String],
      (jValue \ "organization_id").as[Int],
      (jValue \ "organization_name").as[String],
      (jValue \ "department").as[String],
      (jValue \ "avatar_image_url").as[String]
    )
  }

}

