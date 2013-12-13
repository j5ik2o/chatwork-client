package com.github.j5ik2o.chatwork.infrastructure.api.room

import scala.concurrent.{Future, ExecutionContext}
import com.github.j5ik2o.chatwork.infrastructure.api.{ApiService, Client}
import org.jboss.netty.util.CharsetUtil
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.json4s.DefaultReaders._

class MessageApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends ApiService(client.service, client.host, apiToken)
  with MessageApiService {

  def send(roomId: Int, message: String)(implicit executor: ExecutionContext): Future[Int] = {
    val request = createRequestBuilder(s"/v1/rooms/${roomId}/messages").
      addFormElement("body" -> message).
      buildFormPost(false)

    sendRequest(request).flatMap {
      response =>
        println(response.getStatus, response.getContent.toString(CharsetUtil.UTF_8))
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          Future.successful((json \ "message_id").as[Int])
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
