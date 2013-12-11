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

/**
 * {
  "unread_room_num": 2,
  "mention_room_num": 1,
  "mytask_room_num": 3,
  "unread_num": 12,
  "mention_num": 1,
  "mytask_num": 8
}
 */

trait Status {

  val unreadRoomNum: Int
  val mentionRoomNum: Int
  val taskRoomNum: Int
  val mentionNum: Int
  val taskNum: Int
  
}
