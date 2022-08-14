package com.gridgetest.instagram.post.dto

data class GetPostsResDto (
    val nickname: String,
    val profileUrl: String?,
    val imgUrls: List<String>?,
    val contents: String?,
    val numOfLikes: Int,
    val numOfComments: Int,
)