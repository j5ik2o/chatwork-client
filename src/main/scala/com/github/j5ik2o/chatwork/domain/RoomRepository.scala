package com.github.j5ik2o.chatwork.domain

import org.sisioh.dddbase.core.lifecycle.Repository
import scala.concurrent.Future

trait RoomRepository[M[+ _]]
  extends Repository[RoomId, Room, M]

trait AsyncRoomRepository extends RoomRepository[Future]
