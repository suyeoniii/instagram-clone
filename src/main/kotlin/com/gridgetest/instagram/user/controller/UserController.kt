package com.gridgetest.instagram.user.controller

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponse
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.user.domain.UserStatus
import com.gridgetest.instagram.user.dto.*
import com.gridgetest.instagram.user.service.UserService
import com.gridgetest.instagram.util.JwtService
import com.gridgetest.instagram.util.ValidationRegex
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/users")
class UserController(private val userService: UserService, private val jwtService: JwtService) {
    @GetMapping("/{userId}/profile")
    fun getUserProfile(@PathVariable userId: Int): BaseResponse<Profile> {
        try {
            val loginUserId = jwtService.userId
            val getUserRes: Profile = userService.getUserProfile(loginUserId, userId)

            return BaseResponse(getUserRes)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PostMapping("/{userId}/follow")
    fun follow(@PathVariable userId: Int) {
        System.out.println(userId)
        val userId = jwtService.userId
    }

    @PatchMapping("/{userId}")
    fun modifyUser(@PathVariable userId: Int, @RequestBody modifyUserReqDto: ModifyUserReqDto): BaseResponse<ModifyUserResDto> {
        try {
            val user = jwtService.user

            if (modifyUserReqDto.username !== null && modifyUserReqDto.username.length > 21) return BaseResponse(
                BaseResponseCode.USER_USERNAME_INVALID
            )
            if (modifyUserReqDto.nickname !== null && !ValidationRegex.isNameValid(modifyUserReqDto.nickname)) return BaseResponse(
                BaseResponseCode.USER_NICKNAME_INVALID
            )
            val modifyUserResDto = ModifyUserResDto(userService.updateUser(user, modifyUserReqDto.nickname, modifyUserReqDto.username, modifyUserReqDto.profileUrl, modifyUserReqDto.website, modifyUserReqDto.introduction))
            return BaseResponse(modifyUserResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PatchMapping("/{userId}/status")
    fun updateUserStatus(@PathVariable userId: Int, @RequestBody updateUserStatusReqDto: UpdateUserStatusReqDto): BaseResponse<UpdateUserStatusResDto> {
        try {
            val user = jwtService.user

            var status: UserStatus? = null
            if(updateUserStatusReqDto.status == "ACTIVE") status = UserStatus.ACTIVE
            else if(updateUserStatusReqDto.status == "INACTIVE") status = UserStatus.INACTIVE
            else if(updateUserStatusReqDto.status == "DELETE") status = UserStatus.DELETED
            else return BaseResponse(BaseResponseCode.USER_STATUS_INVALID)

            val updateUserStatusResDto = UpdateUserStatusResDto(userService.updateUserStatus(user, status))
            return BaseResponse(updateUserStatusResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }
}