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
package com.github.j5ik2o.chatwork.domain.account

import com.github.j5ik2o.chatwork.domain.RoomId
import java.net.URL
import org.sisioh.dddbase.core.model.EntityCloneable

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

trait Contact extends Account with EntityCloneable[AccountId, Contact] with Ordered[Contact] {

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
