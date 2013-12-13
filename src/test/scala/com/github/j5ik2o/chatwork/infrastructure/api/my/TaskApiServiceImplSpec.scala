package com.github.j5ik2o.chatwork.infrastructure.api.my

import org.specs2.mutable.Specification
import com.github.j5ik2o.chatwork.infrastructure.api.me.MeApiService
import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.{Await, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.ApiThreadPoolExecutor
import scala.concurrent.duration.Duration

class TaskApiServiceImplSpec extends Specification {

  "" should {
    "" in {
      implicit val executor = ExecutionContext.fromExecutorService(ApiThreadPoolExecutor())
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
