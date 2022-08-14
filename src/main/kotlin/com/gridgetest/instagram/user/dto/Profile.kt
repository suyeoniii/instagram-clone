package com.gridgetest.instagram.user.dto

import com.gridgetest.instagram.post.dto.PostSummary

data class Profile(
    val id: Int,
    val nickname: String,
    val username: String?,
    val profileUrl: String?,
    val website: String?,
    val introduction: String?,
    val numOfPosts: Int,
    val numOfFollowers: Int,
    val numOfFollowings: Int,
    val posts: List<PostSummary?>?
)