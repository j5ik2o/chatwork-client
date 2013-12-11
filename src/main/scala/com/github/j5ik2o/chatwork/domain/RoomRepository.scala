package com.github.j5ik2o.chatwork.domain

import org.sisioh.dddbase.core.lifecycle.Repository

trait RoomRepository[M[+ _]]
  extends Repository[RoomId, Room, M] {

}
