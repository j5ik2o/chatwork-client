package com.github.j5ik2o.chatwork.infrastructure.api.my

import com.github.j5ik2o.chatwork.infrastructure.api.{AbstractApiService, Client}
import scala.concurrent.{Future, ExecutionContext}
import org.jboss.netty.handler.codec.http.HttpResponseStatus

private
class StatusApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends AbstractApiService(client.service, client.host, apiToken) with StatusApiService {

  def get(implicit executor: ExecutionContext): Future[Status] = {
    val request = createRequestBuilder("/v1/my/status").buildGet()
    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          Future.successful(Status(json))
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
