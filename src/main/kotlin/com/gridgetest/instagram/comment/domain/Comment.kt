package com.gridgetest.instagram.comment.domain

import com.gridgetest.instagram.config.BaseEntity
import com.gridgetest.instagram.post.domain.Post
import com.gridgetest.instagram.user.domain.User
import javax.persistence.*

@Entity
class Comment(contents: String, status: CommentStatus, post: Post, user: User): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Int? = null

    @Column(nullable = false)
    var contents: String = contents

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CommentStatus = status

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post = post
    // TODO: one to many 연결

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = user
    // TODO: one to many 연결
}