package com.github.j5ik2o.chatwork.infrastructure.api.my

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait TaskApiService {

  def list(params: TaskParams)(implicit executor: ExecutionContext): Future[Seq[Task]]

}

object TaskApiService {

  def apply(client: Client, apiToken: Option[String] = None): TaskApiService =
    new TaskApiServiceImpl(client, apiToken)

}
