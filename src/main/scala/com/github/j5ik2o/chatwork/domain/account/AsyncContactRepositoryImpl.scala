package com.github.j5ik2o.chatwork.domain.account

import com.github.j5ik2o.chatwork.domain.RoomId
import com.github.j5ik2o.chatwork.infrastructure.api.Client
import com.github.j5ik2o.chatwork.infrastructure.api.contact.ContactApiService
import java.net.URL
import java.util.{TimerTask, Timer}
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import scala.concurrent.Future

class AsyncContactRepositoryImpl(client: Client) extends AsyncAccountRepository {
  type This = AsyncAccountRepository

  private val api = ContactApiService(client)

  private val entities = collection.mutable.Map.empty[AccountId, Contact]

  private class Task extends TimerTask {
    def run(): Unit = {
      entities.clear()
    }
  }

  private val timer = new Timer()
  timer.schedule(new Task, 0, 60 * 1000)

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
        }.toSeq
        entities ++= contacts
        contacts
    }
  }

  def resolve(identity: AccountId)(implicit ctx: EntityIOContext[Future]): Future[Contact] = {
    implicit val executor = getExecutionContext(ctx)
    Future(entities(identity))
  }


  def containsByIdentity(identity: AccountId)(implicit ctx: EntityIOContext[Future]): Future[Boolean] = {
    implicit val executor = getExecutionContext(ctx)
    Future(entities.contains(identity))
  }

}
