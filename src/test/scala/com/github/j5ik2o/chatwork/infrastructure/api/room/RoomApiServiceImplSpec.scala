package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.specs2.mutable.Specification
import scala.concurrent.{Await, Future}

import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.duration.Duration
import java.util.concurrent.Executors
import com.github.j5ik2o.chatwork.infrastructure.ApiThreadPoolExecutor

class RoomApiServiceImplSpec extends Specification {

  import scala.concurrent.ExecutionContext

  implicit val executor = ExecutionContext.fromExecutorService(new ApiThreadPoolExecutor())




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
