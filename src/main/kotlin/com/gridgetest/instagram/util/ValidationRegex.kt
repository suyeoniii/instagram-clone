package com.gridgetest.instagram.util

import org.springframework.stereotype.Service

@Service
object ValidationRegex {
    fun isNicknameValid(nickname: String): Boolean {
        val regex = "^[a-z\\d._]{3,20}\$"
        return Regex(regex).containsMatchIn(nickname)
    }
    fun isPasswordValid(password: String): Boolean {
        val regex = "^(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,20}\$"
        return Regex(regex).containsMatchIn(password)
    }
}
