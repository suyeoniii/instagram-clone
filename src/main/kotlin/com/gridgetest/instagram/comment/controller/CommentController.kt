package com.gridgetest.instagram.comment.controller

import com.gridgetest.instagram.comment.dto.PostCommentReqDto
import com.gridgetest.instagram.comment.dto.PostCommentResDto
import com.gridgetest.instagram.comment.service.CommentService
import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponse
import com.gridgetest.instagram.util.JwtService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/comments")
class CommentController(private val commentService: CommentService, private val jwtService: JwtService) {

    @PostMapping
    fun postComment(@RequestBody postCommentReqDto: PostCommentReqDto): BaseResponse<PostCommentResDto> {
        try {
            val user = jwtService.user
            return BaseResponse(PostCommentResDto(commentService.addComment(user, postCommentReqDto)))

        } catch (exception: BaseException) {
            throw exception
        }
    }
}