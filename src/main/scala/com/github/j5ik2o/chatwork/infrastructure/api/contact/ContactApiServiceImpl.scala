package com.github.j5ik2o.chatwork.infrastructure.api.contact

import com.github.j5ik2o.chatwork.infrastructure.api.{AbstractApiService, Client}
import scala.concurrent.{Future, ExecutionContext}
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.json4s._
import org.json4s.DefaultReaders._

class ContactApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends AbstractApiService(client.service, client.host, apiToken) with ContactApiService {

  def list(implicit executor: ExecutionContext): Future[Seq[Contact]] = {
    val request = createRequestBuilder("/v1/contacts").buildGet()

    sendRequest(request).flatMap {
      response =>
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          val contacts = json.as[JArray].arr.map(Contact.apply)
          Future.successful(contacts)
        } else if (response.getStatus == HttpResponseStatus.NO_CONTENT) {
          Future.successful(Seq.empty)
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
