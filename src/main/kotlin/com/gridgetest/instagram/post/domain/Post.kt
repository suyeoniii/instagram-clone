package com.gridgetest.instagram.post.domain

import com.gridgetest.instagram.user.domain.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Post(userId: Int, contents: String, status: PostStatus) {
    @Id @GeneratedValue
    @Column(name = "post_id")
    var id: Int? = null

    @Column(length = 1000)
    var contents: String? = contents

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: PostStatus = status

    @Column(nullable = false)
    @CreationTimestamp
    var createdAt: OffsetDateTime? = null

    @Column(nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
}
