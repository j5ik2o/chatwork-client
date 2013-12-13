package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.ApiService
import org.json4s._
import com.github.j5ik2o.chatwork.infrastructure.api.Client
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.jboss.netty.util.CharsetUtil
import org.json4s.DefaultReaders._

private
class MemberApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends ApiService(client.service, client.host, apiToken) with MemberApiService {

  def list(roomId: Int)(implicit executor: ExecutionContext): Future[Seq[Member]] = {
    val request = createRequestBuilder(s"/v1/rooms/$roomId/members").buildGet()
    sendRequest(request).flatMap {
      response =>
        println(response.getStatus, response.getContent.toString(CharsetUtil.UTF_8))
        if (response.getStatus == HttpResponseStatus.OK) {
          Future.successful(getResponseAsJValue(response).as[JArray].arr.map {
            e =>
              Member(
                (e \ "account_id").as[Int],
                (e \ "role").as[String],
                (e \ "name").as[String],
                (e \ "chatwork_id").as[String],
                (e \ "organization_id").as[Int],
                (e \ "organization_name").as[String],
                (e \ "department").as[String],
                (e \ "avatar_image_url").as[String]
              )
          })
        } else {
          handleErrorResponse(response)
        }
    }
  }

  def update(params: UpdateMemberParams)(implicit executor: ExecutionContext): Future[UpdateResult] = {
    val request = createRequestBuilder(s"/v1/rooms/${params.roomId}/members").
      addFormElement(toTuples(params): _*).
      buildFormPost(false)

    sendRequest(request).flatMap {
      response =>
        println(response.getStatus, response.getContent.toString(CharsetUtil.UTF_8))
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          Future.successful(UpdateResult(
            (json \ "admin").as[JArray].arr.map(e => e.as[Int]).toSeq,
            (json \ "member").as[JArray].arr.map(e => e.as[Int]).toSeq,
            (json \ "readonly").as[JArray].arr.map(e => e.as[Int]).toSeq)
          )
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
