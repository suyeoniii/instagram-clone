package com.gridgetest.instagram.user.service

import com.gridgetest.instagram.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    fun existsUser(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }
}
