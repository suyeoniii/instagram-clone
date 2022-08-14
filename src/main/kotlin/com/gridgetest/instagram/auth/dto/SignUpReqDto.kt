package com.gridgetest.instagram.auth.dto

import java.time.LocalDate

data class SignUpReqDto(
    val nickname: String,
    val password: String,
    val phoneNumber: String,
    val username: String,
    val birth: LocalDate,
)