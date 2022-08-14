package com.gridgetest.instagram.user.domain

import com.gridgetest.instagram.config.BaseEntity
import com.gridgetest.instagram.post.domain.Post
import java.time.LocalDate
import javax.persistence.*

@Entity
class User(
    nickname: String,
    username: String,
    password: String,
    phoneNumber: String,
    birth: LocalDate,
    profileUrl: String?,
    introduction: String?,
    website: String?,
    loginType: String,
): BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    val id: Int? = null

    @Column(nullable = false, length = 30, unique = true)
    var nickname: String = nickname

    @Column(nullable = false, length = 30)
    var username: String = username

    @Column(nullable = false, length = 255)
    var password: String = password

    @Column(nullable = false, length = 20)
    var phoneNumber: String = phoneNumber

    @Column(columnDefinition = "text")
    var profileUrl: String? = profileUrl

    @Column(nullable = false)
    var birth: LocalDate = birth

    @Column
    var website: String? = website

    @Column(length = 100)
    var introduction: String? = introduction

    @Column(nullable = false)
    var loginType: String = loginType

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVE

    @OneToMany(mappedBy = "user")
    var posts: List<Post> = ArrayList<Post>()
}