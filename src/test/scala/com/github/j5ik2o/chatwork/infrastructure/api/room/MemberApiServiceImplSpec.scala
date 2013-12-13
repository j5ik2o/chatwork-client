package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.specs2.mutable.Specification
import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.{ExecutionContext, Await}
import scala.concurrent.duration.Duration
import com.github.j5ik2o.chatwork.infrastructure.ApiThreadPoolExecutor
import com.github.j5ik2o.chatwork.infrastructure.api.me.MeApiService

class MemberApiServiceImplSpec extends Specification {
  val client = ClientFactory.create("api.chatwork.com")
  val meApi = MeApiService(client)
  val roomApi = RoomApiService(client)
  val memberApi = MemberApiService(client)


  implicit val executor = ExecutionContext.fromExecutorService(ApiThreadPoolExecutor())
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
