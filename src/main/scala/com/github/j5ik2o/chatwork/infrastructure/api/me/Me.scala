package com.github.j5ik2o.chatwork.infrastructure.api.me

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


