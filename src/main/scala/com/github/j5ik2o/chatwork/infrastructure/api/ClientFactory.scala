package com.github.j5ik2o.chatwork.infrastructure.api

import com.twitter.finagle.http.Http
import com.twitter.finagle.builder.ClientBuilder

object ClientFactory {

  def create(host: String = "api.chatwork.com") =
    Client(ClientBuilder()
      .codec(Http())
      .hosts(host + ":443")
      .tls(host)
      .hostConnectionLimit(1)
      .build(), host)

}
