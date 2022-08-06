package com.gridgetest.instagram.user.repository

import com.gridgetest.instagram.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun existsByNickname(nickname: String): Boolean
    fun findOneByNicknameAndPassword(nickname: String, password: String): User?
}