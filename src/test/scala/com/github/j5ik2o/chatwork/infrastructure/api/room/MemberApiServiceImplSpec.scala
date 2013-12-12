package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.specs2.mutable.Specification
import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by junichi on 2013/12/13.
 */
class MemberApiServiceImplSpec extends Specification {
  val client = ClientFactory.create("api.chatwork.com")
  val api = RoomApiService(client)
  val memberApi = MemberApiService(client)

  val f = for {
    rooms <- api.list
    roomDetails <- Future.traverse(rooms) {
      r =>
        memberApi.list(r.roomId)
    }
    rr <- Future(roomDetails.flatten)
  } yield {
    println("result = " + rr)
  }

  Await.result(f, Duration.Inf)
  true must beTrue
}
