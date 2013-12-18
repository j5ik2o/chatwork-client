package com.github.j5ik2o.chatwork.infrastructure.api.contact

import com.github.j5ik2o.chatwork.infrastructure.api.Client
import scala.concurrent.{Future, ExecutionContext}

trait ContactApiService {

  def list(implicit executor: ExecutionContext): Future[Seq[Contact]]

}

object ContactApiService {

  def apply(client: Client, apiToken: Option[String] = None): ContactApiService =
    new ContactApiServiceImpl(client, apiToken)

}
