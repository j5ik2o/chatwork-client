package com.github.j5ik2o.chatwork.domain.account

import org.sisioh.dddbase.core.lifecycle.EntityReader
import scala.concurrent.Future
import org.sisioh.dddbase.core.lifecycle.async.AsyncEntityReader

trait AccountRepository[M[+ _]]
  extends EntityReader[AccountId, Account, M]

trait AsyncAccountRepository extends AccountRepository[Future] with AsyncEntityReader[AccountId, Account]


