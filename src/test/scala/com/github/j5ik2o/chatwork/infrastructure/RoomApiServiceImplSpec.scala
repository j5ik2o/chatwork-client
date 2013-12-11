package com.github.j5ik2o.chatwork.infrastructure

import org.specs2.mutable.Specification
import com.twitter.finagle.http.Http
import com.twitter.finagle.builder.ClientBuilder
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class RoomApiServiceImplSpec extends Specification {

  "RoomApiServiceImpl" should {
    "get room entity" in {
      val service = ClientBuilder()
        .codec(Http())
        .hosts("api.chatwork.com:443")
        .tls("api.chatwork.com")
        .hostConnectionLimit(1)
        .build()

      val api = new RoomApiServiceImpl(service, "api.chatwork.com", "7bfeadb074521261d61dc83d2122253b")
      val map = Await.result(api.list, Duration.Inf)

      val roomId = map(0)("roomId")
      println(Await.result(api.get(roomId.asInstanceOf[Int]), Duration.Inf))

      val entity = Room(
        "hoge",
        Some(IconPresets.beer),
        Seq(203450),
        None
      )


      println(Await.result(api.create(entity), Duration.Inf))
      true must beTrue
    }
  }

}
