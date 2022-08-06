package com.gridgetest.instagram.user.controller

import com.gridgetest.instagram.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

}