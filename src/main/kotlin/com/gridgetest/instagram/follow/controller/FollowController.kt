package com.gridgetest.instagram.follow.controller

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponse
import com.gridgetest.instagram.follow.dto.FollowResDto
import com.gridgetest.instagram.follow.service.FollowService
import com.gridgetest.instagram.util.JwtService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/follow")
@Tag(name = "follow", description = "the follow API")
class FollowController(private val followService: FollowService, private val jwtService: JwtService) {
    @PostMapping("/{userId}")
    fun postFollow(@PathVariable userId: Int): BaseResponse<FollowResDto> {
        try {
            val fromUserId = jwtService.userId
            val status = followService.follow(fromUserId, userId)
            return BaseResponse(FollowResDto(status))
        } catch (exception: BaseException) {
            throw exception
        }
    }
}