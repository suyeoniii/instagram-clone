package com.gridgetest.instagram.post.service

import com.gridgetest.instagram.post.domain.Post
import com.gridgetest.instagram.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(private val postRepository: PostRepository) {

    fun getPosts(): List<Post> {
        // TODO: retrieve logged in user id
        return postRepository.findAll()
    }
}
