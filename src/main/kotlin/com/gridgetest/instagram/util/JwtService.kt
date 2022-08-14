package com.gridgetest.instagram.util

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.config.Constant.JWT_EXP
import com.gridgetest.instagram.config.Secret
import com.gridgetest.instagram.user.domain.User
import com.gridgetest.instagram.user.repository.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Service
class JwtService(private val userRepository: UserRepository) {

    fun createJwt(userId: Int): String {
        return Jwts.builder()
            .setHeaderParam("type", "jwt")
            .claim("userId", userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1 * (JWT_EXP)))
            .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
            .compact()
    }

    val jwt: String?
        get() {
            val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
            return request.getHeader("X-ACCESS-TOKEN")
        }

    val userId: Int
        get() {
            if (jwt === null) {
                throw BaseException(BaseResponseCode.JWT_EMPTY)
            }

            val claims: Jws<Claims> = try {
                Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(jwt)
            } catch (ignored: Exception) {
                throw BaseException(BaseResponseCode.INVALID_JWT)
            }

            return claims.body["userId"] as Int
        }

    val user: User
        get() {
            val user = userRepository.findOneById(userId)
            if(user === null) throw BaseException(BaseResponseCode.USER_NOT_FOUND)

            return user
        }

}
