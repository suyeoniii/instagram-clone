package com.gridgetest.instagram.like.service

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.like.domain.PostLike
import com.gridgetest.instagram.like.domain.PostLikeRepository
import com.gridgetest.instagram.like.domain.PostLikeStatus
import com.gridgetest.instagram.post.service.PostService
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostLikeService(
    private val postLikeRepository: PostLikeRepository,
    private val postService: PostService,
    private val userService: UserService
) {
    @Transactional
    fun updatePostLikeStatus(user: User, postId: Int) {
        val post = postService.getPostById(postId)
        val postLike = getPostLike(user.id!!, postId)
        System.out.println("hihi")

        try {
            if (postLike !== null) {
                postLike.status =
                    if (postLike.status === PostLikeStatus.LIKED) PostLikeStatus.UNLIKED else PostLikeStatus.LIKED
                postLikeRepository.save(postLike)
            } else {
                postLikeRepository.save(PostLike(PostLikeStatus.LIKED, post, user))
            }
        } catch (exception: Exception) {
            System.out.println(exception)
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getPostLike(userId: Int, postId: Int): PostLike? {
        try {
            return postLikeRepository.findOneByUserIdAndPostId(userId, postId)
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}