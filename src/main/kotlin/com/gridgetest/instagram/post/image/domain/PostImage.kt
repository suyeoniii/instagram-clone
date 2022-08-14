package com.gridgetest.instagram.post.image.domain

import com.gridgetest.instagram.config.BaseEntity
import com.gridgetest.instagram.post.domain.Post
import javax.persistence.*

@Entity
class PostImage (imgUrl: String, post: Post): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    var id: Long? = null

    @Column(nullable = false, columnDefinition = "text")
    var imgUrl: String = imgUrl

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = post
}
