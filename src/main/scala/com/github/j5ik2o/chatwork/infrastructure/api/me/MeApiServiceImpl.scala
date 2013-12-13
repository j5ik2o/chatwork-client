package com.github.j5ik2o.chatwork.infrastructure.api.me

import com.github.j5ik2o.chatwork.infrastructure.api.{Client, ApiService}
import scala.concurrent.{Future, ExecutionContext}
import org.jboss.netty.handler.codec.http.HttpResponseStatus

class MeApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends ApiService(client.service, client.host, apiToken) with MeApiService {

  def get(implicit executor: ExecutionContext): Future[Me] = {
    val request = createRequestBuilder(s"/v1/me").buildGet()
    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          val me = Me(
            (json \ "account_id").as[Int],
            (json \ "room_id").as[Int],
            (json \ "name").as[String],
            (json \ "chatwork_id").as[String],
            (json \ "organization_id").as[Int],
            (json \ "organization_name").as[String],
            (json \ "department").as[String],
            (json \ "title").as[String],
            (json \ "url").as[String],
            (json \ "introduction").as[String],
            (json \ "mail").as[String],
            (json \ "tel_organization").as[String],
            (json \ "tel_extension").as[String],
            (json \ "tel_mobile").as[String],
            (json \ "skype").as[String],
            (json \ "facebook").as[String],
            (json \ "twitter").as[String],
            (json \ "avatar_image_ur").as[String]
          )
          Future.successful(me)
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
