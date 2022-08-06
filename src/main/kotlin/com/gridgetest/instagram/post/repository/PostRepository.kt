package com.gridgetest.instagram.post.repository

import com.gridgetest.instagram.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Int> {
    fun findAllBy(): List<Post>
}