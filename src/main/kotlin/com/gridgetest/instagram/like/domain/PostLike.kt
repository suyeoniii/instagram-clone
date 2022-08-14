package com.gridgetest.instagram.like.domain

import com.gridgetest.instagram.config.BaseEntity
import com.gridgetest.instagram.post.domain.Post
import com.gridgetest.instagram.user.domain.User
import javax.persistence.*

@Entity
class PostLike(status: PostLikeStatus, post: Post, user: User): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    var id: Long? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: PostLikeStatus = status

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post? = post

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = user
}
