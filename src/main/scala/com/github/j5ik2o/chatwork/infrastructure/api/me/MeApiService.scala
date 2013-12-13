package com.github.j5ik2o.chatwork.infrastructure.api.me

import scala.concurrent.{ExecutionContext, Future}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait MeApiService {

  def get(implicit executor: ExecutionContext): Future[Me]

}


object MeApiService {

  def apply(client: Client, apiToken: Option[String] = None): MeApiService =
    new MeApiServiceImpl(client, apiToken)

}
