package com.github.j5ik2o.chatwork.infrastructure.api.room

import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import org.specs2.mutable.Specification
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class MessageApiServiceImplSpec extends Specification {
  "" should {
    "" in {
      val client = ClientFactory.create("api.chatwork.com")
      val roomService = RoomApiService(client)
      val messageService = MessageApiService(client)
      val f = roomService.list.flatMap {
        result =>
          val room = result.head
          messageService.send(room.roomId, "test")
      }
      val messageId = Await.result(f, Duration.Inf)
      messageId must_!= 0
    }
  }
}
