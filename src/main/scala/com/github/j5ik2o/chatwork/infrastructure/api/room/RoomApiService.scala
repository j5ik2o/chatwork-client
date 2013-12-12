package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.{Client, ActionType}

trait RoomApiService {

  def create(params: CreateRoomParams)
            (implicit executor: ExecutionContext): Future[Int]

  def list(implicit executor: ExecutionContext): Future[Seq[Room]]

  def get(identity: Int)
         (implicit executor: ExecutionContext): Future[Room]

  def update(params: UpdateRoomParams)
            (implicit executor: ExecutionContext): Future[Int]

  def destroy(identity: Int, actionType: ActionType.Value = ActionType.leave)
             (implicit executor: ExecutionContext): Future[Unit]

}

object RoomApiService {

  def apply(client: Client, apiToken: Option[String] = None): RoomApiService =
    new RoomApiServiceImpl(client, apiToken)

}
