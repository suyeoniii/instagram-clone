package com.gridgetest.instagram.auth.controller

import com.gridgetest.instagram.auth.dto.*
import com.gridgetest.instagram.auth.service.AuthService
import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponse
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.util.JwtService
import com.gridgetest.instagram.util.ValidationRegex.isNameValid
import com.gridgetest.instagram.util.ValidationRegex.isPasswordValid
import com.gridgetest.instagram.util.ValidationRegex.isPhoneNumber
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/auth")
class AuthController(private val authService: AuthService, private val jwtService: JwtService) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpReqDto: SignUpReqDto): BaseResponse<SignUpResDto> {
        try {
            if (signUpReqDto.username.isEmpty() || signUpReqDto.username.length > 20) return BaseResponse(
                BaseResponseCode.USER_USERNAME_INVALID
            )
            if (!isPasswordValid(signUpReqDto.password)) return BaseResponse(
                BaseResponseCode.USER_PASSWORD_INVALID
            )
            if (!isPhoneNumber(signUpReqDto.phoneNumber)) return BaseResponse(
                BaseResponseCode.USER_PHONE_NUMBER_LENGTH
            )
            if (!isNameValid(signUpReqDto.nickname)) return BaseResponse(
                BaseResponseCode.USER_NICKNAME_INVALID
            )

            val user = User(
                signUpReqDto.nickname, signUpReqDto.username, signUpReqDto.password, signUpReqDto.phoneNumber,
                signUpReqDto.birth, null, null, null, "E"
            )

            val signUpResult = authService.signUp(user)
            return BaseResponse(signUpResult)
        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginReqDto: LoginReqDto): BaseResponse<LoginResDto> {
        try {
            if (!isNameValid(loginReqDto.nickname)) return BaseResponse(BaseResponseCode.USER_NICKNAME_ALREADY_EXIST)
            if (!isPasswordValid(loginReqDto.password)) return BaseResponse(BaseResponseCode.USER_PASSWORD_INVALID)

            val loginResult = authService.login(loginReqDto.nickname, loginReqDto.password)
            return BaseResponse(loginResult)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @GetMapping("/auto-login")
    fun authLogin(): BaseResponse<AutoLoginResDto> {
        try {
            val userId = jwtService.userId
            val autoLoginResDto = AutoLoginResDto(userId)
            return BaseResponse(autoLoginResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PatchMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable userId: Int,
        @RequestBody updatePasswordReqDto: UpdatePasswordReqDto
    ): BaseResponse<UpdatePasswordResDto> {
        try {
            val updatedUserId = authService.updatePassword(userId, updatePasswordReqDto.password)
            return BaseResponse(UpdatePasswordResDto(updatedUserId))

        } catch (exception: BaseException) {
            throw exception
        }
    }
}