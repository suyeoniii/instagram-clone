package com.gridgetest.instagram.user.service

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.follow.service.FollowService
import com.gridgetest.instagram.post.service.PostService
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.user.domain.UserStatus
import com.gridgetest.instagram.user.dto.Profile
import com.gridgetest.instagram.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val followService: FollowService,
    val postService: PostService
) {

    fun existsUser(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }
    fun getUserById(userId: Int): User {
        val user = userRepository.findOneById(userId)
        if(user === null) throw BaseException(BaseResponseCode.USER_NOT_FOUND)
        try {

            return user
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getUserProfile(loginUserId: Int, userId: Int): Profile {
        val user = userRepository.findOneById(userId)
        if(user === null) throw BaseException(BaseResponseCode.USER_NOT_FOUND)
        val posts = postService.getUserPostList(userId)
        try {

            val numOfFollowers = followService.getCountOfFollowers(userId)
            val numOfFollowings = followService.getCountOfFollowings(userId)
            val numOfPosts = if(posts === null) 0 else posts.size

            return Profile(user.id!!, user.nickname, user.username, user.profileUrl, user.website, user.introduction
                ,numOfPosts, numOfFollowers, numOfFollowings, posts)
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    fun updateUser(user: User, nickname: String?, username: String?, profileUrl: String?, website: String?, introduction: String?): Int {
        try {
            if(nickname !== null) user.nickname = nickname
            if(username !== null) user.username = username
            if(profileUrl !== null) user.profileUrl = profileUrl
            if(website !== null) user.website = website
            if(introduction !== null) user.introduction = introduction

            val updatedUser = userRepository.save(user)
            if(updatedUser === null ) throw BaseException(BaseResponseCode.USER_UPDATE_FAILED)

            return updatedUser.id!!
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    fun updateUserStatus(user: User, status: UserStatus): Int {
        try {
            user.status = status
            System.out.println(user.status)
            val updatedUser = userRepository.save(user)
            if(updatedUser === null ) throw BaseException(BaseResponseCode.USER_UPDATE_FAILED)

            return updatedUser.id!!
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}
