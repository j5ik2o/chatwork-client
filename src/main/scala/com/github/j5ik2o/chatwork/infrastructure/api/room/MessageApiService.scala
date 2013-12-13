package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait MessageApiService {

  def send(roomId: Int, message: String)(implicit executor: ExecutionContext): Future[Int]

}

object MessageApiService {

  def apply(client: Client, apiToken: Option[String] = None): MessageApiService =
    new MessageApiServiceImpl(client, apiToken)

}