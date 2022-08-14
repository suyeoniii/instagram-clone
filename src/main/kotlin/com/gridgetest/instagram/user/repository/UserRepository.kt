package com.gridgetest.instagram.user.repository

import com.gridgetest.instagram.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun existsByNickname(nickname: String): Boolean
    fun findOneByNickname(nickname: String): User?
    fun findOneById(userId: Int): User?
    fun save(user: User): User?
}