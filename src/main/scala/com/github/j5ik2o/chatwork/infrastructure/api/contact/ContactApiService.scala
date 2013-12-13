package com.github.j5ik2o.chatwork.infrastructure.api.contact

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait ContactApiService {

  def list(implicit executor: ExecutionContext): Future[Seq[Contact]]

}

object ContactApiService {

  def appy(client: Client, apiToken: Option[String] = None): ContactApiService =
    new ContactApiServiceImpl(client, apiToken)

}
