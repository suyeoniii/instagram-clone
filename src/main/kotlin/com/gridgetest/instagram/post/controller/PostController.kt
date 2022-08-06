package com.gridgetest.instagram.post.controller

import com.gridgetest.instagram.post.service.PostService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app/posts")
class PostController(private val postService: PostService) {

    @GetMapping
    fun getPosts(@RequestParam pageIndex: Int, @RequestParam size: Int) {
        // TODO: implements
    }
}