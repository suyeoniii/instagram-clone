package com.gridgetest.instagram.like.controller

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.like.service.PostLikeService
import com.gridgetest.instagram.util.JwtService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/posts/{postId}/like")
class PostLikeController(private val postLikeService: PostLikeService, private val jwtService: JwtService) {

    @PostMapping
    fun updateLike(@PathVariable postId: Int) {
        try {
            val user = jwtService.user
            postLikeService.updatePostLikeStatus(user, postId)

        } catch (exception: BaseException) {
            throw exception
        }
    }
}