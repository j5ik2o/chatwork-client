package com.github.j5ik2o.chatwork.domain.room

import com.github.j5ik2o.chatwork.domain.room.Room.Member
import com.github.j5ik2o.chatwork.infrastructure.api.Client
import com.github.j5ik2o.chatwork.infrastructure.api.room.{MemberApiService, RoomApiService}
import com.github.j5ik2o.chatwork.infrastructure.api.room.{Room => InfraRoom, Member => InfraMember}
import java.net.URL
import java.util.Date
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.sisioh.dddbase.core.lifecycle.async.AsyncEntityIOContext
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import com.github.j5ik2o.chatwork.domain.{Organization, AccountId}


class AsyncRoomRepositoryImplSpec extends Specification with Mockito {

  class Setup extends Scope {
    val mockClient = mock[Client]
    val mockRoomApiService = mock[RoomApiService]
    val mockMemberApiService = mock[MemberApiService]

    val repos = new AsyncRoomRepositoryImpl(mockClient, None) {
      override private[room] val roomApiService: RoomApiService = mockRoomApiService
      override private[room] val memberApiService: MemberApiService = mockMemberApiService
    }

    implicit val ctx = AsyncEntityIOContext()

  }

  private def createRoom() = {
    val roomJson = """{
                     |  "room_id": 123,
                     |  "name": "Group Chat Name",
                     |  "type": "group",
                     |  "role": "admin",
                     |  "sticky": false,
                     |  "unread_num": 10,
                     |  "mention_num": 1,
                     |  "mytask_num": 0,
                     |  "message_num": 122,
                     |  "file_num": 10,
                     |  "task_num": 17,
                     |  "icon_path": "https://example.com/ico_group.png",
                     |  "last_update_time": 1298905200,
                     |  "description": "room description text"
                     |}""".stripMargin

    val memberJson = """
                       |  {
                       |    "account_id": 123,
                       |    "role": "member",
                       |    "name": "John Smith",
                       |    "chatwork_id": "tarochatworkid",
                       |    "organization_id": 101,
                       |    "organization_name": "Hello Company",
                       |    "department": "Marketing",
                       |    "avatar_image_url": "https://example.com/abc.png"
                       |  }
                     """.stripMargin

    val infraMember = InfraMember(parse(memberJson))
    val infraRoom = InfraRoom.single(parse(roomJson))
    val member = Member(
      identity = AccountId(infraMember.acountId),
      roomId = RoomId(infraRoom.roomId),
      name = infraMember.name,
      chatWorkId = infraMember.chatWorkId,
      organization = Organization(infraMember.organizationId,infraMember.organizationName),
      department = infraMember.department,
      avatarImageUrl = new URL(infraMember.avatarImageUrl),
      role = infraMember.role
    )
    val entity = Room(
      identity = RoomId(infraRoom.roomId),
      name = infraRoom.name,
      roomType = RoomType.withName(infraRoom.roomType),
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
      members = Seq(member)
    )
    (infraRoom, infraMember, entity)
  }

  "repository" should {
    "be able to get entities" in new Setup {
      val (infraRoom, infraMember, entity) = createRoom()
      mockRoomApiService.list returns Future(Seq(infraRoom))
      mockMemberApiService.list(infraRoom.roomId) returns Future(Seq(infraMember))
      val future = repos.resolveAll
      Await.result(future, Duration.Inf) must_== Seq(entity)
    }
    "be able to get empty entities" in new Setup {
      mockRoomApiService.list returns Future(Seq.empty)
      val future = repos.resolveAll
      Await.result(future, Duration.Inf) must_== Seq.empty
    }
    "be able to get entity" in new Setup {
      val (infraRoom, infraMember, entity) = createRoom()
      mockRoomApiService.get(entity.identity.value) returns Future(infraRoom)
      mockMemberApiService.list(infraRoom.roomId) returns Future(Seq(infraMember))
      val future = repos.resolve(entity.identity)
      Await.result(future, Duration.Inf) must_== entity
    }
  }

}
