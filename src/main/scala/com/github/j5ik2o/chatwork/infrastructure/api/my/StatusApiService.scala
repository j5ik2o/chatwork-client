package com.github.j5ik2o.chatwork.infrastructure.api.my

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait StatusApiService {

  def get(implicit executor: ExecutionContext): Future[Status]

}

object StatusApiService {

  def apply(client: Client, apiToken: Option[String] = None): StatusApiService =
    new StatusApiServiceImpl(client, apiToken)

}
