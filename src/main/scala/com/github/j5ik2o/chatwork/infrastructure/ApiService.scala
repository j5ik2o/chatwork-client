/*
 * Copyright 2013 Junichi Kato. (http://j5ik2o.me/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.github.j5ik2o.chatwork.infrastructure

import org.jboss.netty.buffer.ChannelBuffers._
import com.twitter.finagle.Service
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpVersion._
import org.jboss.netty.util.CharsetUtil._
import com.twitter.finagle.http.Request
import scala.concurrent.{Future, ExecutionContext}
import scala.collection.JavaConverters._
import FutureUtil._

abstract class ApiService(service: Service[HttpRequest, HttpResponse]) {

  protected def sendRequest
  (method: HttpMethod,
   path: String,
   params: Map[String, Option[String]] = Map.empty,
   headers: Map[String, Any] = Map.empty,
   body: Option[String] = None)
  (implicit executor: ExecutionContext): Future[HttpResponse] = {
    val paramsString = params.toList.map {
      case (key, Some(value)) => Some("%s=%s".format(key, value))
      case _ => None
    }.flatten.mkString("&")
    val request = new DefaultHttpRequest(HTTP_1_1, method, if (params.isEmpty) path else path + "?" + paramsString)
    headers.foreach {
      case (k, v: Iterable[_]) =>
        request.setHeader(k, v.asJava)
      case (k, v) =>
        request.setHeader(k, v)
    }
    body.foreach(e => request.setContent(copiedBuffer(e, UTF_8)))
    service(Request(request)).toScala.map {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) response
        else throw new RuntimeException(response.toString)
    }
  }
}
