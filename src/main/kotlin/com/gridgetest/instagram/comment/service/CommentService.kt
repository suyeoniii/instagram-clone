package com.gridgetest.instagram.comment.service

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.comment.domain.Comment
import com.gridgetest.instagram.comment.domain.CommentRepository
import com.gridgetest.instagram.comment.domain.CommentStatus
import com.gridgetest.instagram.comment.dto.PostCommentReqDto
import com.gridgetest.instagram.post.service.PostService
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.util.JwtService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(private val commentRepository: CommentRepository, private val postService: PostService, private val jwtService: JwtService) {

    @Transactional
    fun addComment(user: User, postCommentReqDto: PostCommentReqDto): Int {
        val post = postService.getPostById(postCommentReqDto.postId)
        try {
            val comment = Comment(postCommentReqDto.contents, CommentStatus.ACTIVE, post, user)
            val uploadedComment: Comment? = commentRepository.save(comment)
            if(uploadedComment === null) throw BaseException(BaseResponseCode.COMMENT_UPLOAD_FAILED)
            return uploadedComment.id!!
        }
        catch(exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getCommentById(commentId: Int):Comment {
        try {
            val comment = commentRepository.findOneById(commentId)
            if(comment === null) throw BaseException(BaseResponseCode.COMMENT_NOT_FOUND)
            return comment
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getNumberOfComments(postId: Int): Int {
        try {
            return commentRepository.countByPostId(postId)
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}
