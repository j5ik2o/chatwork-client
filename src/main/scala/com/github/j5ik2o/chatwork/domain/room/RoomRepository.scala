package com.github.j5ik2o.chatwork.domain.room

import com.github.j5ik2o.chatwork.infrastructure.api.Client
import org.sisioh.dddbase.core.lifecycle.EntityReader
import org.sisioh.dddbase.core.lifecycle.async.{AsyncEntityReadableBySeq, AsyncEntityReader}
import scala.concurrent.Future

trait RoomRepository[M[+ _]]
  extends EntityReader[RoomId, Room, M]

trait AsyncRoomRepository
  extends RoomRepository[Future]
  with AsyncEntityReader[RoomId, Room]
  with AsyncEntityReadableBySeq[RoomId, Room]

object AsyncRoomRepository {

  def apply(client: Client, apiToken: Option[String] = None): AsyncRoomRepository =
    new AsyncRoomRepositoryImpl(client, apiToken)

}
