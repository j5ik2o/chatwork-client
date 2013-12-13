package com.github.j5ik2o.chatwork.infrastructure.api.me

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
  "title": "CMO",
  "url": "http://mycompany.com",
  "introduction": "Self Introduction",
  "mail": "taro@example.com",
  "tel_organization": "XXX-XXXX-XXXX",
  "tel_extension": "YYY-YYYY-YYYY",
  "tel_mobile": "ZZZ-ZZZZ-ZZZZ",
  "skype": "myskype_id",
  "facebook": "myfacebook_id",
  "twitter": "mytwitter_id",
  "avatar_image_url": "https://example.com/abc.png"
}
 */
case class Me
(accountId: Int,
 roomId: Int,
 name: String,
 chatWorkId: String,
 organizationId: Int,
 organizationName: String,
 department: String,
 title: String,
 url: String,
 introduction: String,
 mail: String,
 telOrganization: String,
 telExtension: String,
 telMobile: String,
 skype: String,
 facebook: String,
 twitter: String,
 avatarImageUrl: String)

object Me {
  def apply(jValue: JValue): Me = {
    Me(
      (jValue \ "account_id").as[Int],
      (jValue \ "room_id").as[Int],
      (jValue \ "name").as[String],
      (jValue \ "chatwork_id").as[String],
      (jValue \ "organization_id").as[Int],
      (jValue \ "organization_name").as[String],
      (jValue \ "department").as[String],
      (jValue \ "title").as[String],
      (jValue \ "url").as[String],
      (jValue \ "introduction").as[String],
      (jValue \ "mail").as[String],
      (jValue \ "tel_organization").as[String],
      (jValue \ "tel_extension").as[String],
      (jValue \ "tel_mobile").as[String],
      (jValue \ "skype").as[String],
      (jValue \ "facebook").as[String],
      (jValue \ "twitter").as[String],
      (jValue \ "avatar_image_url").as[String]
    )
  }
}
