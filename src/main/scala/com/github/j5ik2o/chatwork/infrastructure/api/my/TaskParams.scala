package com.github.j5ik2o.chatwork.infrastructure.api.my

import com.github.j5ik2o.chatwork.infrastructure.api.RequestParams

case class TaskParams(assignedByAccountId: Option[Int] = None, status: Option[String] = None)
  extends RequestParams {

  def toMap: Map[String, Any] = {
    toMap("assigned_by_account_id", assignedByAccountId) ++
      toMap("status", status)
  }

}
