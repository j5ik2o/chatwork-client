package com.github.j5ik2o.chatwork.domain.contact

import org.sisioh.dddbase.core.lifecycle.EntityReader
import scala.concurrent.Future
import org.sisioh.dddbase.core.lifecycle.async.{AsyncEntityReadableBySeq, AsyncEntityReader}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait ContactRepository[M[+ _]]
  extends EntityReader[AccountId, Contact, M]

trait AsyncContactRepository
  extends ContactRepository[Future]
  with AsyncEntityReader[AccountId, Contact]
  with AsyncEntityReadableBySeq[AccountId, Contact]

object AsyncContactRepository {
  def apply(client: Client, apiToken: Option[String] = None): AsyncContactRepository =
    new AsyncContactRepositoryImpl(client, apiToken)
}

