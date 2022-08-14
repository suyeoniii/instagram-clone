package com.gridgetest.instagram.post.controller

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponse
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.post.domain.Post
import com.gridgetest.instagram.post.domain.PostStatus
import com.gridgetest.instagram.post.dto.*
import com.gridgetest.instagram.post.service.PostService
import com.gridgetest.instagram.util.JwtService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/posts")
@Tag(name = "post", description = "the post API")
class PostController(private val postService: PostService, private val jwtService: JwtService) {

    @GetMapping
    fun getFollowerPosts(@RequestParam pageIndex: Int, @RequestParam size: Int): BaseResponse<List<GetPostsResDto?>?> {
        val userId = jwtService.userId
        val posts = postService.getPosts(userId, pageIndex*size, size)
        return BaseResponse(posts)
    }

    @PostMapping
    fun uploadPost(@RequestBody uploadPostReqDto: UploadPostReqDto): BaseResponse<UploadPostResDto> {
        try {
            val user = jwtService.user
            if(uploadPostReqDto.imgUrls.isEmpty() || uploadPostReqDto.imgUrls.size > 10) return BaseResponse(BaseResponseCode.POST_IMAGE_COUNT_ERROR)

            // TODO: post image url validation
            val postId: Int? = postService.uploadPost(user, uploadPostReqDto.contents, uploadPostReqDto.imgUrls)
            if(postId === null) return BaseResponse(BaseResponseCode.POST_REGISTER_FAILED)

            val uploadPostResDto = UploadPostResDto(postId)
            return BaseResponse(uploadPostResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PatchMapping("/{postId}")
    fun modifyPost(@PathVariable postId: Int, @RequestBody modifyPostReqDto: ModifyPostReqDto): BaseResponse<ModifyPostResDto> {
        try {
            val user = jwtService.user
            if(modifyPostReqDto.contents.length > 1000) return BaseResponse(BaseResponseCode.POST_CONTENTS_LENGTH)

            val modifyPostResDto = ModifyPostResDto(postService.updatePost(user, postId, modifyPostReqDto.contents, null))
            return BaseResponse(modifyPostResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PatchMapping("/{postId}/status")
    fun deletePost(@PathVariable postId: Int): BaseResponse<DeletePostResDto> {
        try {
            val user = jwtService.user

            val deletePostResDto = DeletePostResDto(postService.updatePost(user, postId, null, PostStatus.DELETED))
            return BaseResponse(deletePostResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }
}