package com.github.j5ik2o.chatwork.domain.room

import org.sisioh.dddbase.core.lifecycle.{RepositoryException, EntityNotFoundException, EntityIOContext}
import scala.concurrent.Future
import com.github.j5ik2o.chatwork.infrastructure.api.{ApiException, Client}
import com.github.j5ik2o.chatwork.infrastructure.api.room.{MemberApiService, RoomApiService}
import java.util.Date
import java.net.URL
import com.github.j5ik2o.chatwork.domain.contact.AccountId

class AsyncRoomRepositoryImpl(client: Client, apiToken: Option[String] = None) extends AsyncRoomRepository {

  private val roomApiService = RoomApiService(client, apiToken)

  private val memberApiService = MemberApiService(client, apiToken)

  private def getMembers(roomId: RoomId)(implicit ctx: EntityIOContext[Future]): Future[Seq[Member]] = {
    implicit val executor = getExecutionContext(ctx)
    memberApiService.list(roomId.value).map {
      members =>
        members.map {
          m =>
            Room.Member(
              AccountId(m.acountId),
              roomId,
              m.name,
              m.chatWorkId,
              m.organizationId,
              m.organizationName,
              m.department,
              new URL(m.avatarImageUrl),
              m.role

            )
        }
    }
  }

  import com.github.j5ik2o.chatwork.infrastructure.api.room.{Room => InfraRoom}

  private def convertToEntity(infraRoom: InfraRoom)(implicit ctx: EntityIOContext[Future]) = {
    implicit val executor = getExecutionContext(ctx)
    val roomId = RoomId(infraRoom.roomId)
    getMembers(roomId).map {
      members =>
        Room(
          identity = RoomId(infraRoom.roomId),
          name = infraRoom.name,
          roomType = RoomType.withName(infraRoom.roomRole),
          roomRole = RoomRole.withName(infraRoom.roomRole),
          sticky = infraRoom.sticky,
          description = infraRoom.description,
          unreadNum = infraRoom.unreadNum,
          mentionNum = infraRoom.mentionNum,
          myTaskNum = infraRoom.mytaskNum,
          messageNum = infraRoom.messageNum,
          fileNum = infraRoom.fileNum,
          taskNum = infraRoom.taskNum,
          iconPath = infraRoom.iconPath,
          lastUpdateTime = infraRoom.lastUpdateTime.map(new Date(_)),
          members = members
        )
    }

  }

  def resolveAll(implicit ctx: EntityIOContext[Future]): Future[Seq[Room]] = {
    implicit val executor = getExecutionContext(ctx)
    roomApiService.list.flatMap {
      infraRooms =>
        Future.traverse(infraRooms)(convertToEntity)
    }.recoverWith {
      case ex: ApiException =>
        Future.failed(RepositoryException())
    }
  }

  def resolve(identity: RoomId)(implicit ctx: EntityIOContext[Future]): Future[Room] = {
    implicit val executor = getExecutionContext(ctx)
    roomApiService.get(identity.value).map {
      e =>
        Room(
          identity = RoomId(e.roomId),
          name = e.name,
          roomType = RoomType.withName(e.roomRole),
          roomRole = RoomRole.withName(e.roomRole),
          sticky = e.sticky,
          description = e.description,
          unreadNum = e.unreadNum,
          mentionNum = e.mentionNum,
          myTaskNum = e.mytaskNum,
          messageNum = e.messageNum,
          fileNum = e.fileNum,
          taskNum = e.taskNum,
          iconPath = e.iconPath,
          lastUpdateTime = e.lastUpdateTime.map(new Date(_)),
          members = Seq.empty
        )
    }.recoverWith {
      case ex: ApiException =>
        Future.failed(EntityNotFoundException(Some(s"identity = $identity"), Some(ex)))
    }
  }

  def containsByIdentity(identity: RoomId)(implicit ctx: EntityIOContext[Future]): Future[Boolean] = {
    implicit val executor = getExecutionContext(ctx)
    resolve(identity).map(_ => true).recover {
      case ex: EntityNotFoundException => false
    }
  }

}
