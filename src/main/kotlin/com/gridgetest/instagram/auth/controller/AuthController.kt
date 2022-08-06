package com.gridgetest.instagram.auth.controller

import com.gridgetest.instagram.auth.dto.*
import com.gridgetest.instagram.auth.service.AuthService
import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponse
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.config.RegExp
import com.gridgetest.instagram.util.JwtService
import com.gridgetest.instagram.util.ValidationRegex.isNicknameValid
import com.gridgetest.instagram.util.ValidationRegex.isPasswordValid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/app/auth")
class AuthController(private val authService: AuthService, private val jwtService: JwtService) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpReqDto: SignUpReqDto): BaseResponse<SignUpResDto> {
        try {
            if (signUpReqDto.username.length < 3 || signUpReqDto.username.length > 20) return BaseResponse(
                BaseResponseCode.USER_USERNAME_LENGTH
            )
            if (!isPasswordValid(signUpReqDto.password)) return BaseResponse(
                BaseResponseCode.USER_PASSWORD_INVALID
            )
            if (!Regex(RegExp.PHONE).containsMatchIn(signUpReqDto.phoneNumber)) return BaseResponse(
                BaseResponseCode.USER_PHONE_NUMBER_LENGTH
            )
            if (!isNicknameValid(signUpReqDto.nickname)) return BaseResponse(
                BaseResponseCode.USER_NICKNAME_INVALID
            )

            val signUpResult = authService.signUp(signUpReqDto)
            return BaseResponse(signUpResult)
        } catch (exception: BaseException) {
            throw exception
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginReqDto: LoginReqDto): BaseResponse<LoginResDto> {
        try {
            if (!isNicknameValid(loginReqDto.nickname)) return BaseResponse(BaseResponseCode.USER_NICKNAME_ALREADY_EXIST)
            if (!isPasswordValid(loginReqDto.password)) return BaseResponse(BaseResponseCode.USER_PASSWORD_INVALID)

            val loginResult = authService.login(loginReqDto)
            return BaseResponse(loginResult)

        } catch (exception: BaseException) {
            throw exception
        }
    }

    @GetMapping("/auto-login")
    fun authLogin(): BaseResponse<AutoLoginResDto> {
        try {
            val userId = jwtService.userIdx
            val autoLoginResDto = AutoLoginResDto(userId)
            return BaseResponse(autoLoginResDto)

        } catch (exception: BaseException) {
            throw exception
        }
    }
}