package com.github.j5ik2o.chatwork.domain.room

import org.sisioh.dddbase.core.lifecycle.EntityReader
import scala.concurrent.Future
import org.sisioh.dddbase.core.lifecycle.async.{AsyncEntityReadableBySeq, AsyncEntityReader}

trait RoomRepository[M[+ _]]
  extends EntityReader[RoomId, Room, M]

trait AsyncRoomRepository
  extends RoomRepository[Future]
  with AsyncEntityReader[RoomId, Room]
  with AsyncEntityReadableBySeq[RoomId, Room]
