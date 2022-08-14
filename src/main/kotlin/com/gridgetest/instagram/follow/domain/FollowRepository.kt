package com.gridgetest.instagram.follow.domain

import com.gridgetest.instagram.follow.domain.Follow
import com.gridgetest.instagram.follow.domain.FollowStatus
import com.gridgetest.instagram.follow.dto.FollowCount
import com.gridgetest.instagram.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: JpaRepository<Follow, Int> {
    fun existsFollowByFromUserIdAndToUserId(fromUserId: Int, toUserId: Int): Boolean
    @Modifying
    @Query(value = "INSERT INTO follow(from_user_id, to_user_id, status) VALUES(:fromUserId, :toUserId, :status)", nativeQuery = true)
    fun follow(fromUserId: Int, toUserId: Int, status: String)
    @Query(value = "SELECT COUNT(*) FROM follow f JOIN user u ON u.user_id = f.from_user_id WHERE from_user_id = :fromUserId AND (u.status = 'ACTIVE' OR u.status = 'PRIVATE')", nativeQuery = true)
    fun countByFromUserId(fromUserId: Int): Int
    @Query(value = "SELECT COUNT(*) FROM follow f JOIN user u ON u.user_id = f.to_user_id WHERE to_user_id = :toUserId AND (u.status = 'ACTIVE' OR u.status =  'PRIVATE')", nativeQuery = true)
    fun countByToUserId(toUserId: Int): Int
}