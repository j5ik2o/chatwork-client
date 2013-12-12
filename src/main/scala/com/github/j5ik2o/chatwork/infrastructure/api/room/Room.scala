package com.github.j5ik2o.chatwork.infrastructure.api.room

case class Room
(roomId: Int,
 name: String,
 roomType: String,
 roomRole: String,
 sticky: Boolean,
 unreadNum: Int,
 mentionNum: Int,
 mytaskNum: Int,
 messageNum: Int,
 fileNum: Int,
 taskNum: Int,
 iconPath: String,
 description: Option[String] = None,
 lastUpdateTime: Option[Long] = None)

