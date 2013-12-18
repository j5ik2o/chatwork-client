package com.github.j5ik2o.chatwork.domain.account

import org.sisioh.dddbase.core.lifecycle.EntityReader
import scala.concurrent.Future
import org.sisioh.dddbase.core.lifecycle.async.{AsyncEntityReadableBySeq, AsyncEntityReader}
import com.github.j5ik2o.chatwork.infrastructure.api.Client

trait AccountRepository[M[+ _]]
  extends EntityReader[AccountId, Account, M]

trait AsyncAccountRepository
  extends AccountRepository[Future]
  with AsyncEntityReader[AccountId, Account]
  with AsyncEntityReadableBySeq[AccountId, Account]

object AsyncContactRepository {
  def apply(client: Client, apiToken: Option[String] = None): AsyncAccountRepository =
    new AsyncAccountRepositoryImpl(client, apiToken)
}

