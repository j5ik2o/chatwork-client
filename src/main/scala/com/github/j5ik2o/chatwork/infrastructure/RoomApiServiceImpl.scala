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
import org.jboss.netty.handler.codec.http.{HttpResponse, HttpRequest}
import scala.concurrent.{Future, ExecutionContext}
import org.json4s.jackson.JsonMethods._
import org.jboss.netty.util.CharsetUtil._
import org.json4s.DefaultReaders._
import org.json4s.JArray

import FutureUtil._

object IconPresets extends Enumeration {
  val group, check, document, meeting, event,
  project, business, study, security, star, idea, heart, magcup, beer, music, sports, travel = Value
}


case class Room
(name: String,
 iconPreset: Option[IconPresets.Value],
 membersAdminIds: Seq[Int],
 membersMemberIds: Option[Seq[Int]]) {
  def toMap: Map[String, Any] = {
    Map(
      "name" -> name,
      "members_admin_ids" -> membersAdminIds.mkString(",")
    ) ++ iconPreset.map {
      e =>
        Map(
          "icon_preset" -> e.toString
        )
    }.getOrElse(Map.empty) ++ membersMemberIds.map {
      e =>
        Map(
          "members_member_ids" -> e.mkString(",")
        )
    }.getOrElse(Map.empty)
  }
}


class RoomApiServiceImpl(service: Service[HttpRequest, HttpResponse], host: String, apiToken: String)
  extends ApiService(service, host, apiToken) {

  def create(entity: Room)(implicit executor: ExecutionContext): Future[Map[String, Any]] = {
    val tuples = entity.toMap.map {
      case (k, v) => (k, v.toString)
    }.toSeq
    val req = createRequestBuilder("/v1/rooms").addFormElement(tuples: _*).buildFormPost(false)
    service(req).toScala.map {
      response =>
        val json = response.getContent.toString(UTF_8)
        val jValue = parse(json)
        println(pretty(jValue))
        Map(
          "roomId" -> (jValue \ "room_id").as[String]
        )
    }
  }

  def list(implicit executor: ExecutionContext): Future[Seq[Map[String, Any]]] = {
    val request = createRequestBuilder("/v1/rooms").buildGet()
    sendRequest(request).map {
      response =>
        val json = response.getContent.toString(UTF_8)
        val jValue = parse(json)
        // println(pretty(jValue))
        val array = jValue.as[JArray]
        array.arr.map {
          e =>
            Map(
              "roomId" -> (e \ "room_id").as[Int],
              "name" -> (e \ "name").as[String],
              "type" -> (e \ "type").as[String],
              "role" -> (e \ "role").as[String],
              "sticky" -> (e \ "sticky").as[Boolean],
              "unreadNum" -> (e \ "unread_num").as[Int],
              "mentionNum" -> (e \ "mention_num").as[Int],
              "mytaskNum" -> (e \ "mytask_num").as[Int],
              "messageNum" -> (e \ "message_num").as[Int],
              "fileNum" -> (e \ "file_num").as[Int],
              "taskNum" -> (e \ "task_num").as[Int],
              "iconPath" -> (e \ "icon_path").as[String],
              "lastUpdateTime" -> (e \ "last_update_time").as[Long]
            )
        }
    }
  }

  def get(identity: Int)(implicit executor: ExecutionContext): Future[Map[String, Any]] = {
    val request = createRequestBuilder(s"/v1/rooms/$identity").buildGet()
    sendRequest(request).map {
      response =>
        val jValue = parse(response.getContent.toString(UTF_8))
        // println(pretty(jValue))
        Map(
          "roomId" -> (jValue \ "room_id").as[Int],
          "name" -> (jValue \ "name").as[String],
          "type" -> (jValue \ "type").as[String],
          "role" -> (jValue \ "role").as[String],
          "sticky" -> (jValue \ "sticky").as[Boolean],
          "unreadNum" -> (jValue \ "unread_num").as[Int],
          "mentionNum" -> (jValue \ "mention_num").as[Int],
          "mytaskNum" -> (jValue \ "mytask_num").as[Int],
          "messageNum" -> (jValue \ "message_num").as[Int],
          "fileNum" -> (jValue \ "file_num").as[Int],
          "taskNum" -> (jValue \ "task_num").as[Int],
          "iconPath" -> (jValue \ "icon_path").as[String],
          "description" -> (jValue \ "description").as[String]
        )
    }
  }

  def update(entity: Map[String, Any])(implicit executor: ExecutionContext): Future[Map[String, Any]] = ???

  def destroy(identity: Int)(implicit executor: ExecutionContext): Future[Map[String, Any]] = ???

}
