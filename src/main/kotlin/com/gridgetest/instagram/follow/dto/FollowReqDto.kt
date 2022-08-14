package com.gridgetest.instagram.follow.dto

import com.gridgetest.instagram.follow.domain.FollowStatus

data class FollowReqDto (
    val userId: Int,
    val status: FollowStatus
)