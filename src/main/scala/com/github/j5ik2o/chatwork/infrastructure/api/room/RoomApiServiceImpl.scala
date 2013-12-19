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
package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpMethod}
import scala.concurrent.{Future, ExecutionContext}
import org.json4s.DefaultReaders._
import org.json4s.JArray

import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.util.CharsetUtil
import com.github.j5ik2o.chatwork.infrastructure.api.{Client, AbstractApiService, ActionType}
import org.sisioh.scala.toolbox.LoggingEx

private
class RoomApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends AbstractApiService(client.service, client.host, apiToken)
  with RoomApiService with LoggingEx {

  def create(params: CreateRoomParams)
            (implicit executor: ExecutionContext): Future[Int] = {
    val request = createRequestBuilder("/v1/rooms").
      addFormElement(toForm(params): _*).
      buildFormPost(false)

    sendRequest(request).map {
      response =>
        (getResponseAsJValue(response) \ "room_id").as[Int]
    }
  }


  def list(implicit executor: ExecutionContext): Future[Seq[Room]] = withDebugScope("list") {
    scopedDebug("createRequestBuilder")
    val request = createRequestBuilder("/v1/rooms").buildGet()
    scopedDebug(s"sendRequest($request)")
    sendRequest(request).map {
      response =>
        val json = getResponseAsJValue(response)
        json.as[JArray].arr.map(Room.listEntry)
    }
  }

  def get(identity: Int)
         (implicit executor: ExecutionContext): Future[Room] = {
    val request = createRequestBuilder(s"/v1/rooms/$identity").buildGet()

    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          Future.successful(Room.single(json))
        } else {
          handleErrorResponse(response)
        }
    }
  }


  def update(params: UpdateRoomParams)
            (implicit executor: ExecutionContext): Future[Int] = {
    val request = createRequestBuilder(s"/v1/rooms/${params.roomId}").
      addFormElement(toForm(params): _*).
      buildFormPost(false)

    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) {
          Future.successful((getResponseAsJValue(response) \ "room_id").as[Int])
        } else {
          handleErrorResponse(response)
        }
    }
  }

  def destroy(identity: Int, actionType: ActionType.Value = ActionType.leave)
             (implicit executor: ExecutionContext): Future[Unit] = {
    val content = Some(ChannelBuffers.copiedBuffer(s"action_type=$actionType", CharsetUtil.UTF_8))
    val request = createRequestBuilder(s"/v1/rooms/${identity}").build(HttpMethod.DELETE, content)

    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.NO_CONTENT) {
          Future.successful()
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
