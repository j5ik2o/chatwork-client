package com.github.j5ik2o.chatwork.infrastructure.api.my

import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import com.github.j5ik2o.chatwork.infrastructure.api.me.MeApiService
import org.specs2.mutable.Specification
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class TaskApiServiceImplSpec extends Specification {

  "" should {
    "" in {
      val client = ClientFactory.create("api.chatwork.com")
      val meApi = MeApiService(client)
      val taskApi = TaskApiService(client)
      val f = for {
        me <- meApi.get
        tasks <- taskApi.list(TaskParams())
      } yield {
        println(me, tasks)
      }

      Await.result(f, Duration.Inf)
      true must beTrue
    }
  }

}
