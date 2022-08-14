package com.gridgetest.instagram.post.service

import com.gridgetest.instagram.comment.domain.CommentRepository
import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.post.domain.Post
import com.gridgetest.instagram.post.domain.PostRepository
import com.gridgetest.instagram.post.domain.PostStatus
import com.gridgetest.instagram.post.dto.GetPostsResDto
import com.gridgetest.instagram.post.dto.PostSummary
import com.gridgetest.instagram.post.image.domain.PostImage
import com.gridgetest.instagram.post.image.domain.PostImageRepository
import com.gridgetest.instagram.like.domain.PostLikeRepository
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.util.JwtService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(private val postRepository: PostRepository, private val postImageRepository: PostImageRepository, private val postLikeRepository: PostLikeRepository, private val commentRepository: CommentRepository, private val jwtService: JwtService) {

    fun getPosts(userId: Int, pageIndex: Int, size: Int): List<GetPostsResDto?> {
        val posts = postRepository.findFollowingPosts(userId, pageIndex, size)

        val postList: ArrayList<GetPostsResDto> = arrayListOf()
        posts.forEach{
            val numOfLikes = postLikeRepository.countByPostId(it.id!!)
            val numOfComments = commentRepository.countByPostId(it.id!!)
            val images: ArrayList<String> = arrayListOf()
            it.images.forEach{i -> images.add(i.imgUrl)}
            System.out.println(images)
            postList.add(GetPostsResDto(it.user!!.nickname, it.user!!.profileUrl, images, it!!.contents, numOfLikes, numOfComments))
        }

        return postList
    }

    @Transactional
    fun uploadPost(user: User, contents: String, imgUrls: List<String>): Int? {
        try {
            val post = Post(contents, PostStatus.ACTIVE, user)

            // upload post
            val uploadedPost: Post? = postRepository.save(post)
            if(uploadedPost === null) throw BaseException(BaseResponseCode.POST_UPLOAD_FAILED)

            // upload post image
            val postImages: ArrayList<PostImage> = arrayListOf()
            imgUrls.forEach{ it -> postImages.add(PostImage(it, uploadedPost))}
            postImageRepository.saveAll(postImages)

            System.out.println(uploadedPost.id)

            return uploadedPost.id
        }
        catch(exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getPostById(postId: Int): Post {
        val post = postRepository.findOneById(postId)
        if(post === null) throw BaseException(BaseResponseCode.POST_NOT_FOUND)
        return post
    }

    fun getUserPostList(userId: Int): List<PostSummary?>? {
        try {
            val posts: List<Post?> = postRepository.findAll(userId)
            val postSummary: ArrayList<PostSummary> = arrayListOf()
            posts.forEach{ postSummary.add(PostSummary(it!!.id!!, it.images[0].imgUrl)) }
            return postSummary
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    @Transactional
    fun updatePost(user: User, postId: Int, contents: String?, status: PostStatus?): Int {
        val post = getPostById(postId)
        if(post.user !== user) throw BaseException(BaseResponseCode.POST_USER_NOT_MATCH)

        try {
            if(contents !== null) post.contents = contents
            if(status !== null) post.status = status

            val updatedPost = postRepository.save(post)
            if(updatedPost === null) throw BaseException(BaseResponseCode.POST_UPDATE_FAILED)

            return updatedPost.id!!
        }
        catch(exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}
