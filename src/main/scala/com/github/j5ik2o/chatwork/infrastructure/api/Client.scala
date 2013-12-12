package com.github.j5ik2o.chatwork.infrastructure.api

import com.twitter.finagle.Service
import org.jboss.netty.handler.codec.http.{HttpResponse, HttpRequest}

case class Client(service: Service[HttpRequest, HttpResponse], host: String)

