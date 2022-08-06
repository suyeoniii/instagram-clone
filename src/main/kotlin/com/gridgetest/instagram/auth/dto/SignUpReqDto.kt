package com.gridgetest.instagram.auth.dto

import java.time.LocalDate

data class SignUpReqDto(
    var nickname: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var username: String = "",
    var birth: LocalDate,
)