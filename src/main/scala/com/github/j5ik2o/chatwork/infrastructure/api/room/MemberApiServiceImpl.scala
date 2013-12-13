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
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          Future.successful(json.as[JArray].arr.map(Member.apply))
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
          Future.successful(UpdateResult(json))
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
