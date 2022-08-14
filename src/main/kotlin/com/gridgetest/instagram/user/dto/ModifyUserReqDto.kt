package com.gridgetest.instagram.user.dto

data class ModifyUserReqDto(
    val nickname: String?,
    val username: String?,
    val profileUrl: String?,
    val website: String?,
    val introduction: String?,
)