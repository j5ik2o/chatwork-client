package com.github.j5ik2o.chatwork.infrastructure.api.room

import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import com.github.j5ik2o.chatwork.infrastructure.api.me.MeApiService
import org.specs2.mutable.Specification
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class MemberApiServiceImplSpec extends Specification {
  val client = ClientFactory.create("api.chatwork.com")
  val meApi = MeApiService(client)
  val roomApi = RoomApiService(client)
  val memberApi = MemberApiService(client)


  "s" should {
    "v" in {

      val f = for {
        me <- meApi.get
        rooms <- roomApi.list
      } yield {
        println("result = " +(me, rooms))
      }

      Await.result(f, Duration.Inf)
      true must beTrue
    }
  }


}
