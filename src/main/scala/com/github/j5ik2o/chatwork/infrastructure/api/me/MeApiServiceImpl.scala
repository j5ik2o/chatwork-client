package com.github.j5ik2o.chatwork.infrastructure.api.me

import com.github.j5ik2o.chatwork.infrastructure.api.{Client, AbstractApiService}
import scala.concurrent.{Future, ExecutionContext}
import org.jboss.netty.handler.codec.http.HttpResponseStatus

private
class MeApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends AbstractApiService(client.service, client.host, apiToken) with MeApiService {

  def get(implicit executor: ExecutionContext): Future[Me] = {
    val request = createRequestBuilder("/v1/me").buildGet()
    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          Future.successful(Me(json))
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
