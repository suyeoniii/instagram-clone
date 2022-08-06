package com.gridgetest.instagram.auth.service

import com.gridgetest.instagram.auth.dto.LoginReqDto
import com.gridgetest.instagram.auth.dto.LoginResDto
import com.gridgetest.instagram.auth.dto.SignUpReqDto
import com.gridgetest.instagram.auth.dto.SignUpResDto
import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.user.repository.UserRepository
import com.gridgetest.instagram.user.service.UserService
import com.gridgetest.instagram.util.JwtService
import org.springframework.stereotype.Service

@Service
class AuthService(private val userService: UserService, private val userRepository: UserRepository, private val jwtService: JwtService) {
    fun signUp(signUpReqDto: SignUpReqDto): SignUpResDto {
        try {
            if(userService.existsUser(signUpReqDto.nickname)) throw BaseException(BaseResponseCode.USER_NICKNAME_ALREADY_EXIST)

            // TODO: 비밀번호 암호화

            // DB: insert User
            val encryptedUser = User(signUpReqDto.nickname, signUpReqDto.username, signUpReqDto.password, signUpReqDto.phoneNumber,
            signUpReqDto.birth, null, null, null, "E")

            val registeredUser = userRepository.save(encryptedUser)

            // TODO: insert 실패

            val token = jwtService.createJwt(registeredUser.id!!)
            return SignUpResDto(registeredUser.id, token)
        }
        catch(exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun login(loginReqDto: LoginReqDto): LoginResDto {
        // TODO: 비밀번호 암호화

        val loginUser = userRepository.findOneByNicknameAndPassword(loginReqDto.nickname, loginReqDto.password)
        if(loginUser === null) throw BaseException(BaseResponseCode.USER_LOGIN_FAILED)

        try {
            val token = jwtService.createJwt(loginUser.id!!)
            return LoginResDto(loginUser.id, token)
        }
        catch(exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}