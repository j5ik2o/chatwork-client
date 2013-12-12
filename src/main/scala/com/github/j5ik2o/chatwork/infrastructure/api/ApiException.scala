package com.github.j5ik2o.chatwork.infrastructure.api

case class ApiException(messages: List[String]) extends Exception(messages.mkString(","))


