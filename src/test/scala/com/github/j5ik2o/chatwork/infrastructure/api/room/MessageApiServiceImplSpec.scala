package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.specs2.mutable.Specification
import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration.Duration
import com.github.j5ik2o.chatwork.infrastructure.ApiThreadPoolExecutor

class MessageApiServiceImplSpec extends Specification {
  implicit val executor = ExecutionContext.fromExecutorService(ApiThreadPoolExecutor())
  "" should {
    "" in {
      val client = ClientFactory.create("api.chatwork.com")
      val roomService = RoomApiService(client)
      val messageService = MessageApiService(client)
      val f = roomService.list.flatMap{
        result =>
          val room = result.head
          messageService.send(room.roomId, "test")
      }
      val messageId = Await.result(f, Duration.Inf)
      messageId must_!= 0
    }
  }
}
