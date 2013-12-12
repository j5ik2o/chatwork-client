package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.specs2.mutable.Specification
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.j5ik2o.chatwork.infrastructure.api.room.RoomApiService
import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.duration.Duration

class RoomApiServiceImplSpec extends Specification {

  "RoomApiServiceImpl" should {
    "get room entity" in {

      val client = ClientFactory.create("api.chatwork.com")
      val api = RoomApiService(client)

      val f = for {
        rooms <- api.list
        roomDetails <- Future.traverse(rooms) {
          r => api.get(r.roomId)
        }
      } yield {
        println(roomDetails)
      }

      Await.result(f, Duration.Inf)
      true must beTrue
    }
  }

}
