package com.github.j5ik2o.chatwork.infrastructure.api.room

/**
 * "account_id": 123,
    "role": "member",
    "name": "John Smith",
    "chatwork_id": "tarochatworkid",
    "organization_id": 101,
    "organization_name": "Hello Company",
    "department": "Marketing",
    "avatar_image_url": "https://example.com/abc.png"
 */
case class Member
(acountId: Int,
 role: String,
 name: String,
 chatWorkId: String,
 organizationId: Int,
 organizationName: String,
 department: String,
 avatarImageUrl: String)

