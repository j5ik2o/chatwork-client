package com.github.j5ik2o.chatwork.infrastructure.api.room

case class UpdateResult(admin: Seq[Int], member: Seq[Int], readonly: Seq[Int])

