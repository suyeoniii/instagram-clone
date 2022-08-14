package com.gridgetest.instagram.like.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostLikeRepository : JpaRepository<PostLike, Int> {
    fun findOneByUserIdAndPostId(user: Int, postId: Int): PostLike? //TODO: user status
    @Query("SELECT COUNT(*) FROM post_like pl WHERE post_id = :postId And pl.status = 'LIKED'", nativeQuery = true)
    fun countByPostId(postId: Int): Int
}