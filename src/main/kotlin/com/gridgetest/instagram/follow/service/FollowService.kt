package com.gridgetest.instagram.follow.service

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.follow.domain.FollowRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowService(private val followRepository: FollowRepository) {
    fun existsFollow(fromUserId: Int, toUserId: Int): Boolean {
        return followRepository.existsFollowByFromUserIdAndToUserId(fromUserId, toUserId)
    }

    @Transactional
    fun follow(fromUserId: Int, toUserId: Int): String {
        try {
            var followStatus: String = "FOLLOW"
            if (existsFollow(fromUserId, toUserId)) followStatus = "UNFOLLOW"

            followRepository.follow(fromUserId, toUserId, followStatus)
            return followStatus
        } catch (exception: BaseException) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getCountOfFollowers(userId: Int): Int {
        try {
            return followRepository.countByFromUserId(userId)
        } catch (exception: BaseException) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getCountOfFollowings(userId: Int): Int {
        try {
            return followRepository.countByToUserId(userId)
        } catch (exception: BaseException) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}