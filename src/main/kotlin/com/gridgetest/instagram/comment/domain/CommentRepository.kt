package com.gridgetest.instagram.comment.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Int> {
    fun save(comment: Comment): Comment?
    fun findOneById(commentId: Int): Comment?
    fun countByPostId(postId: Int): Int
}