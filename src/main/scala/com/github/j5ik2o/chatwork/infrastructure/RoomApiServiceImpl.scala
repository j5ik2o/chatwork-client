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
import org.jboss.netty.handler.codec.http.{HttpMethod, HttpResponse, HttpRequest}
import scala.concurrent.{Future, ExecutionContext}
import org.json4s.jackson.JsonMethods._
import org.jboss.netty.util.CharsetUtil._
import scala.Some

class RoomApiServiceImpl(service: Service[HttpRequest, HttpResponse])
  extends ApiService(service) with CrudApiService[Int] {

  def create(entity: Map[String, Any])(implicit executor: ExecutionContext): Future[Map[String, Any]] = {
    val body = Some(entity.map {
      e =>
        Seq(e._1, "=", e._2).mkString
    }.toList.mkString("&"))
    sendRequest(HttpMethod.POST, "/v1/rooms", Map.empty, Map.empty, body).map {
      response =>
        val json = parse(response.getContent.toString(UTF_8))
        Map(
          "description" -> (json \ "description").as[String]
        )
    }
  }

  def get(identity: Int)(implicit executor: ExecutionContext): Future[Map[String, Any]] = ???

  def update(entity: Map[String, Any])(implicit executor: ExecutionContext): Future[Map[String, Any]] = ???

  def destroy(identity: Int)(implicit executor: ExecutionContext): Future[Map[String, Any]] = ???

}
