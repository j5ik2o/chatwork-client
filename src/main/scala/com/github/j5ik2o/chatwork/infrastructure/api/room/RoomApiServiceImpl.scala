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
import com.github.j5ik2o.chatwork.infrastructure.api.{Client, ApiService, ActionType}
import org.sisioh.scala.toolbox.LoggingEx

private
class RoomApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends ApiService(client.service, client.host, apiToken)
  with RoomApiService with LoggingEx {

  def create(params: CreateRoomParams)
            (implicit executor: ExecutionContext): Future[Int] = {
    val request = createRequestBuilder("/v1/rooms").
      addFormElement(toTuples(params): _*).
      buildFormPost(false)

    sendRequest(request).map {
      response =>
        (getResponseAsJValue(response) \ "room_id").as[Int]
    }
  }


  def list(implicit executor: ExecutionContext): Future[Seq[Room]] = {
    val request = createRequestBuilder("/v1/rooms").buildGet()

    sendRequest(request).map {
      response =>
        getResponseAsJValue(response).as[JArray].arr.map {
          e =>
            Room(
              (e \ "room_id").as[Int],
              (e \ "name").as[String],
              (e \ "type").as[String],
              (e \ "role").as[String],
              (e \ "sticky").as[Boolean],
              (e \ "unread_num").as[Int],
              (e \ "mention_num").as[Int],
              (e \ "mytask_num").as[Int],
              (e \ "message_num").as[Int],
              (e \ "file_num").as[Int],
              (e \ "task_num").as[Int],
              (e \ "icon_path").as[String],
              None,
              Some((e \ "last_update_time").as[Long])
            )
        }
    }
  }

  def get(identity: Int)
         (implicit executor: ExecutionContext): Future[Room] = {
    val request = createRequestBuilder(s"/v1/rooms/$identity").buildGet()

    sendRequest(request).map {
      response =>
        val jValue = getResponseAsJValue(response)
        Room(
          (jValue \ "room_id").as[Int],
          (jValue \ "name").as[String],
          (jValue \ "type").as[String],
          (jValue \ "role").as[String],
          (jValue \ "sticky").as[Boolean],
          (jValue \ "unread_num").as[Int],
          (jValue \ "mention_num").as[Int],
          (jValue \ "mytask_num").as[Int],
          (jValue \ "message_num").as[Int],
          (jValue \ "file_num").as[Int],
          (jValue \ "task_num").as[Int],
          (jValue \ "icon_path").as[String],
          Some((jValue \ "description").as[String])
        )
    }
  }


  def update(params: UpdateRoomParams)
            (implicit executor: ExecutionContext): Future[Int] = {
    val request = createRequestBuilder(s"/v1/rooms/${params.roomId}").
      addFormElement(toTuples(params): _*).
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
