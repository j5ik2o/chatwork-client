package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait MemberApiService {

  def list(roomId: Int)(implicit executor: ExecutionContext): Future[Seq[Member]]

  def update(params: UpdateMemberParams)(implicit executor: ExecutionContext): Future[UpdateResult]

}

object MemberApiService {

  def apply(client: Client, apiToken: Option[String] = None): MemberApiService =
    new MemberApiServiceImpl(client, apiToken)

}
