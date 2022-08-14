package com.gridgetest.instagram.post.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Int> {
    fun findOneById(id: Int): Post?
    fun save(post: Post): Post?
    @Query(value = "SELECT * FROM post p WHERE user_id = :userId AND p.status = 'ACTIVE'", nativeQuery = true)
    fun findAll(userId: Int): List<Post?>
    @Query(
        value = "SELECT * FROM post p WHERE user_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :userId) AND p.status = 'ACTIVE' ORDER BY p.post_id DESC LIMIT :pageIndex, :size",
        nativeQuery = true
    )
    fun findFollowingPosts(userId: Int, pageIndex: Int, size: Int): List<Post>

}