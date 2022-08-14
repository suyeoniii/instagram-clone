package com.gridgetest.instagram.util

import org.springframework.stereotype.Service

@Service
object ValidationRegex {
    fun isNameValid(nickname: String): Boolean {
        val regex = "^[a-z\\d._]{3,20}\$"
        return Regex(regex).containsMatchIn(nickname)
    }
    fun isPasswordValid(password: String): Boolean {
        val regex = "^(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,20}\$"
        return Regex(regex).containsMatchIn(password)
    }
    fun isPhoneNumber(phoneNumber: String): Boolean {
        val regex = "^[\\d]{1,11}\$"
        return Regex(regex).containsMatchIn(phoneNumber)
    }
}
