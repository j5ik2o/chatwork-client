package com.github.j5ik2o.chatwork.infrastructure.api.room

import com.github.j5ik2o.chatwork.infrastructure.api.{RequestParams, IconPresets}

/**
 * Created by junichi.kato on 2013/12/12.
 */
case class UpdateRoomParams
(roomId: Int,
 name: Option[String],
 iconPreset: Option[IconPresets.Value],
 description: Option[String]) extends RequestParams {

  def toMap: Map[String, Any] = {
    toMap("name", name) ++
      toMap("icon_preset", iconPreset) ++
      toMap("description", description)
  }
}
