package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.concurrent.{Future, ExecutionContext}

trait MessageApiService {

  def send(roomId: Int, message: String)(implicit executor: ExecutionContext): Future[Int]

}
