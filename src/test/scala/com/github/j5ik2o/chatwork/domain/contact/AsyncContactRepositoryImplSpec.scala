package com.github.j5ik2o.chatwork.domain.contact

import org.specs2.mutable.Specification
import com.github.j5ik2o.chatwork.infrastructure.api.ClientFactory
import scala.concurrent.ExecutionContext.Implicits.global
import org.sisioh.dddbase.core.lifecycle.async.AsyncEntityIOContext
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class AsyncContactRepositoryImplSpec extends Specification {

 val client = ClientFactory.create()
 val repos = new AsyncContactRepositoryImpl(client)

  "s" should {
    "v" in {
      implicit val ctx = AsyncEntityIOContext()
      val e = repos.resolveAll
      val result = Await.result(e, Duration.Inf)
      println(result)
      true must_== true
    }
  }

}
