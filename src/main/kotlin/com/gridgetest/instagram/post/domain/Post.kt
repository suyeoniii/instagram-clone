package com.gridgetest.instagram.post.domain

import com.gridgetest.instagram.config.BaseEntity
import com.gridgetest.instagram.post.image.domain.PostImage
import com.gridgetest.instagram.like.domain.PostLike
import com.gridgetest.instagram.user.domain.User
import javax.persistence.*

@Entity
class Post(contents: String, status: PostStatus, user: User): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var id: Int? = null

    @Column(length = 1000)
    var contents: String? = contents

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: PostStatus = status

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = user

    @OneToMany(mappedBy = "post")
    var images: List<PostImage> = ArrayList<PostImage>()

    @OneToMany(mappedBy = "post")
    var likes: List<PostLike> = ArrayList<PostLike>()
}
