package com.github.j5ik2o.chatwork.infrastructure.api.my

import com.github.j5ik2o.chatwork.infrastructure.api.{AbstractApiService, Client}
import scala.concurrent.{Future, ExecutionContext}
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import org.json4s._
import org.json4s.DefaultReaders._

class TaskApiServiceImpl(client: Client, apiToken: Option[String] = None)
  extends AbstractApiService(client.service, client.host, apiToken) with TaskApiService {

  def list(params: TaskParams)(implicit executor: ExecutionContext): Future[Seq[Task]] = {
    val paramsText = params.toMap.map(e => Seq(e._1, "=", e._2).mkString).mkString("&")
    val url = if (paramsText.size > 0) Seq("/v1/my/tasks", paramsText).mkString("?") else "/v1/my/tasks"
    println(url)
    val request = createRequestBuilder(url).buildGet()
    sendRequest(request).flatMap {
      response =>
        println(response)
        if (response.getStatus == HttpResponseStatus.OK) {
          val json = getResponseAsJValue(response)
          val tasks = json.as[JArray].arr.map(Task.apply)
          Future.successful(tasks)
        } else if (response.getStatus == HttpResponseStatus.NO_CONTENT) {
          Future.successful(Seq.empty[Task])
        } else {
          handleErrorResponse(response)
        }
    }
  }

}
