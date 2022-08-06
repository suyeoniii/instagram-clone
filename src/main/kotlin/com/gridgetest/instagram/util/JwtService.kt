package com.gridgetest.instagram.util

import com.gridgetest.instagram.config.BaseException
import com.gridgetest.instagram.config.BaseResponseCode
import com.gridgetest.instagram.config.Secret
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Service
class JwtService {

    fun createJwt(userId: Int): String {
        return Jwts.builder()
            .setHeaderParam("type", "jwt")
            .claim("userId", userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
            .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
            .compact()
    }

    val jwt: String
        get() {
            val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
            return request.getHeader("X-ACCESS-TOKEN")
        }

    val userIdx: Int
        get() {
            val accessToken = jwt
            if (accessToken.isEmpty()) {
                throw BaseException(BaseResponseCode.JWT_EMPTY)
            }

            val claims: Jws<Claims> = try {
                Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken)
            } catch (ignored: Exception) {
                throw BaseException(BaseResponseCode.INVALID_JWT)
            }

            return claims.body["userId"] as Int
        }
}
