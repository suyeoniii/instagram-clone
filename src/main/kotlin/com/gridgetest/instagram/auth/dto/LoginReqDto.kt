package com.gridgetest.instagram.auth.dto

data class LoginReqDto(
    var nickname: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var username: String = "",
)