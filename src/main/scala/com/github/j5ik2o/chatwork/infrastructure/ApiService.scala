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

import com.twitter.finagle.Service
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.http.{Request, RequestBuilder}
import scala.concurrent.{Future, ExecutionContext}
import FutureUtil._

abstract class ApiService(service: Service[HttpRequest, HttpResponse], host: String, apiToken: String) {

  protected def createRequestBuilder(path: String) =
    RequestBuilder().url("http://" + host + path).addHeader("X-ChatWorkToken", apiToken)

  protected def sendRequest(request: HttpRequest)(implicit executor: ExecutionContext): Future[HttpResponse] =
    service(Request(request)).toScala.flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) Future.successful(response)
        else Future.failed(new RuntimeException(response.toString))
    }


}
