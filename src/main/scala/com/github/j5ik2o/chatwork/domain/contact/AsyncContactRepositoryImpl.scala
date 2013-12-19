package com.github.j5ik2o.chatwork.domain.contact

import com.github.j5ik2o.chatwork.infrastructure.api.Client
import com.github.j5ik2o.chatwork.infrastructure.api.contact.ContactApiService
import java.net.URL
import java.util.{TimerTask, Timer}
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._
import scala.concurrent._
import org.sisioh.scala.toolbox.LoggingEx
import com.github.j5ik2o.chatwork.domain.room.RoomId
import com.github.j5ik2o.chatwork.domain.{Organization, AccountId}

private[contact]
class AsyncContactRepositoryImpl(client: Client, apiToken: Option[String] = None, cachedTime: Option[Int] = Some(60 * 1000))
  extends AsyncContactRepository with LoggingEx {
  type This = AsyncContactRepository

  private[contact] val apiService = ContactApiService(client, apiToken)

  private val caches = new ConcurrentHashMap[AccountId, Contact]()

  private val timer = new Timer()

  private object Task extends TimerTask {
    def run(): Unit = withDebugScope("caches.clear()") {
      caches.clear()
    }
  }

  cachedTime.foreach {
    t =>
      timer.schedule(Task, 0, t)
  }

  private def loadEntities(implicit ctx: EntityIOContext[Future]) = {
    implicit val executor = getExecutionContext(ctx)
    apiService.list.map {
      result =>
        val contacts = result.map {
          e =>
            val id = AccountId(e.accountId)
            val v = Contact(
              identity = id,
              roomId = RoomId(e.roomId),
              name = e.name,
              chatWorkId = e.chatWorkId,
              organization = Organization(e.organizationId, e.organizationName),
              department = e.department,
              avatarImageUrl = new URL(e.avatarImageUrl)
            )
            (id, v)
        }.toMap
        caches.putAll(contacts.asJava)
        contacts
    }
  }

  def resolve(identity: AccountId)(implicit ctx: EntityIOContext[Future]): Future[Contact] = {
    implicit val executor = getExecutionContext(ctx)
    containsByIdentity(identity).flatMap {
      result =>
        if (result) {
          Future(caches.get(identity))
        } else {
          loadEntities.map {
            _ =>
              caches.get(identity)
          }
        }
    }
  }


  def containsByIdentity(identity: AccountId)(implicit ctx: EntityIOContext[Future]): Future[Boolean] = {
    implicit val executor = getExecutionContext(ctx)
    future {
      caches.contains(identity)
    }
  }

  def resolveAll(implicit ctx: EntityIOContext[Future]): Future[Seq[Contact]] = {
    implicit val executor = getExecutionContext(ctx)
    def entitiesToSeq = {
      caches.entrySet().asScala.map {
        e =>
          e.getValue
      }.toList
    }
    future {
      entitiesToSeq
    }.flatMap {
      result =>
        if (result.isEmpty) {
          loadEntities.map(_ => entitiesToSeq)
        } else {
          Future(Seq.empty)
        }
    }
  }
}
