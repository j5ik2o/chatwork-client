package com.github.j5ik2o.chatwork.infrastructure.api

trait RequestParams {

  def toMap: Map[String, Any]

  protected def toMap[V](name: String, v: Option[V]) = {
    v.map {
      e => Map(name -> e.toString)
    }.getOrElse(Map.empty[String, Any])
  }

  protected def toMap[V](name: String, values: Seq[V]) = {
    if (values.isEmpty)
      Map.empty[String, Any]
    else
      Map(name -> values.mkString(","))
  }

}
