package com.gridgetest.instagram.follow.domain

import com.gridgetest.instagram.config.BaseEntity
import com.gridgetest.instagram.user.domain.User
import javax.persistence.*

@Entity
class Follow(): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    val id: Int? = null

    @Enumerated(EnumType.STRING)
    var status: FollowStatus? = FollowStatus.FOLLOW

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    var fromUser: User? = null

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    var toUser: User? = null
}
