package com.gridgetest.instagram.auth.dto

data class LoginResDto(
    val userId: Int,
    val jwt: String,
)