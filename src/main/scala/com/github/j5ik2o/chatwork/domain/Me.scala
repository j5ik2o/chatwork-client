/*
 * Copyright 2013 Junichi Kato. (http://j5ik2o.me/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.github.j5ik2o.chatwork.domain

import java.net.URL

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
trait Me {
  val accountId: AccountId
  val roomId: RoomId
  val name: String
  val chatWorkId: String
  val organizationId: Int
  val organizationName: String
  val department: String
  val title: String
  val url: URL
  val introduction: String
  val mail: String
  val telOrganization: String
  val telExtension: String
  val telMobile: String
  val skype: String
  val facebook: String
  val twitter: String
  val avatarImageUrl: URL
}
