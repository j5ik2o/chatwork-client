package com.github.j5ik2o.chatwork.domain.contact

import com.github.j5ik2o.chatwork.domain.RoomId
import com.github.j5ik2o.chatwork.infrastructure.api.Client
import com.github.j5ik2o.chatwork.infrastructure.api.contact.ContactApiService
import java.net.URL
import java.util.{TimerTask, Timer}
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._
import scala.concurrent._

class AsyncContactRepositoryImpl(client: Client, apiToken: Option[String] = None) extends AsyncContactRepository {
  type This = AsyncContactRepository

  private val api = ContactApiService(client, apiToken)

  private val entities = new ConcurrentHashMap[AccountId, Contact]()

  private val timer = new Timer()

  private object Task extends TimerTask {
    def run(): Unit = {
      entities.clear()
    }
  }

  timer.schedule(Task, 0, 60 * 1000)

  private def loadEntities(implicit ctx: EntityIOContext[Future]) = {
    implicit val executor = getExecutionContext(ctx)
    api.list.map {
      result =>
        val contacts = result.map {
          e =>
            val id = AccountId(e.accountId)
            val v = Contact(
              identity = id,
              roomId = RoomId(e.roomId),
              name = e.name,
              chatWorkId = e.chatWorkId,
              organizationId = e.organizationId,
              organizationName = e.organizationName,
              department = e.department,
              avatarImageUrl = new URL(e.avatarImageUrl)
            )
            (id, v)
        }.toMap
        entities.putAll(contacts.asJava)
        contacts
    }
  }

  def resolve(identity: AccountId)(implicit ctx: EntityIOContext[Future]): Future[Contact] = synchronized {
    implicit val executor = getExecutionContext(ctx)
    containsByIdentity(identity).flatMap {
      result =>
        if (result) {
          Future(entities.get(identity))
        } else {
          loadEntities.map {
            _ =>
              entities.get(identity)
          }
        }
    }
  }


  def containsByIdentity(identity: AccountId)(implicit ctx: EntityIOContext[Future]): Future[Boolean] = {
    implicit val executor = getExecutionContext(ctx)
    Future(entities.contains(identity))
  }

  def resolveAll(implicit ctx: EntityIOContext[Future]): Future[Seq[Contact]] = {
    implicit val executor = getExecutionContext(ctx)
    def entitiesToSeq = {
      entities.entrySet().asScala.map {
        e =>
          e.getValue
      }.toSeq
    }
    future {
      entitiesToSeq
    }.flatMap{
      result =>
        if(result.isEmpty) {
          loadEntities.map(_ => entitiesToSeq)
        } else {
          Future(Seq.empty)
        }
    }
  }
}
