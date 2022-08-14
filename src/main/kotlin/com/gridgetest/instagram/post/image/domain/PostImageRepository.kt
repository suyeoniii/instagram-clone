package com.gridgetest.instagram.post.image.domain

import com.gridgetest.instagram.post.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostImageRepository : JpaRepository<PostImage, Int> {
    fun save(post: Post): Post?
}