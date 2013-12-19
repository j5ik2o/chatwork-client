package com.github.j5ik2o.chatwork.domain.contact

import org.specs2.mutable.Specification
import com.github.j5ik2o.chatwork.infrastructure.api.Client
import scala.concurrent.ExecutionContext.Implicits.global
import org.sisioh.dddbase.core.lifecycle.async.AsyncEntityIOContext
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import org.specs2.mock.Mockito
import com.github.j5ik2o.chatwork.infrastructure.api.contact.ContactApiService
import org.specs2.specification.Scope
import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.github.j5ik2o.chatwork.infrastructure.api.contact.{Contact => InfraContact}
import com.github.j5ik2o.chatwork.domain.room.RoomId
import java.net.URL
import com.github.j5ik2o.chatwork.domain.{Organization, AccountId}

class AsyncContactRepositoryImplSpec extends Specification with Mockito {

  class Setup extends Scope {
    val mockClient = mock[Client]
    val mockApiService = mock[ContactApiService]
    val repos = new AsyncContactRepositoryImpl(mockClient, None, None) {
      override private[contact] val apiService: ContactApiService = mockApiService
    }
    implicit val ctx = AsyncEntityIOContext()

  }

  private def createContact() = {
    val json = """
                 |{
                 |    "account_id": 123,
                 |    "room_id": 322,
                 |    "name": "John Smith",
                 |    "chatwork_id": "tarochatworkid",
                 |    "organization_id": 101,
                 |    "organization_name": "Hello Company",
                 |    "department": "Marketing",
                 |    "avatar_image_url": "https://example.com/abc.png"
                 |}
               """.stripMargin

    val infraContact = InfraContact(parse(json))
    val entity = Contact(
      identity = AccountId(infraContact.accountId),
      roomId = RoomId(infraContact.roomId),
      name = infraContact.name,
      chatWorkId = infraContact.chatWorkId,
      organization = Organization(infraContact.organizationId, infraContact.organizationName),
      department = infraContact.department,
      avatarImageUrl = new URL(infraContact.avatarImageUrl)
    )
    (infraContact, entity)
  }


  "repository" should {
    "be able to get entities" in new Setup {
      val (infraContact, entity) = createContact()
      mockApiService.list returns Future(Seq(infraContact))
      val future = repos.resolveAll
      Await.result(future, Duration.Inf) must_== Seq(entity)
    }
    "be able to get empty entities" in new Setup {
      mockApiService.list returns Future(Seq.empty)
      val future = repos.resolveAll
      Await.result(future, Duration.Inf) must_== Seq.empty
    }
    "be able to get entity" in new Setup {
      val (infraContact, entity) = createContact()
      mockApiService.list returns Future(Seq(infraContact))
      val future = repos.resolve(entity.identity)
      Await.result(future, Duration.Inf) must_== entity
    }
  }

}
