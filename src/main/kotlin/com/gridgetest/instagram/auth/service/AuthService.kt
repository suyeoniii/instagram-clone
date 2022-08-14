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
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.Tuple

@Service
class AuthService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    fun signUp(user: User): SignUpResDto {
        if (userService.existsUser(user.nickname)) throw BaseException(BaseResponseCode.USER_NICKNAME_ALREADY_EXIST)

        try {
            user.password = passwordEncoder.encode(user.password)

            // DB: insert User
            val registeredUser = userRepository.save(user)
            if (registeredUser === null) throw BaseException(BaseResponseCode.USER_REGISTER_FAILED)

            val token = jwtService.createJwt(registeredUser.id!!)
            return SignUpResDto(registeredUser.id, token)
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun login(nickname: String, password: String): LoginResDto {
        val user = userRepository.findOneByNickname(nickname)
        if (user === null || !passwordEncoder.matches(password, user.password)) throw BaseException(BaseResponseCode.USER_LOGIN_FAILED)

        try {
            val token = jwtService.createJwt(user.id!!)
            return LoginResDto(user.id, token)
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun updatePassword(userId: Int, password: String): Int {
        val user: User? = userService.getUserById(userId)
        if(user === null) throw BaseException(BaseResponseCode.USER_NOT_FOUND)

        user.password = passwordEncoder.encode(password)
        userRepository.save(user)
        try {
            return user.id!!
        } catch (exception: Exception) {
            throw BaseException(BaseResponseCode.INTERNAL_SERVER_ERROR)
        }
    }
}