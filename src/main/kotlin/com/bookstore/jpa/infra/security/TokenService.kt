package com.bookstore.jpa.infra.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.bookstore.jpa.models.UserModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {

    @Value("\${api.security.token.secret}")
    private lateinit var secret: String

    fun generateToken(user: UserModel): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)

            val token = JWT.create()
                .withIssuer("login-auth-api")
                .withSubject(user.email)
                .withExpiresAt(this.generateExpirationDate())
                .sign(algorithm)

            return token
        }catch (exception: JWTCreationException) {
            throw RuntimeException("Error while authenticating")
        }
    }

    fun validateToken(token: String): String? {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            return JWT.require(algorithm)
                .withIssuer("login-auth-api")
                .build()
                .verify(token)
                .subject
        }catch (exception: JWTVerificationException) {
            return null
        }
    }

    private fun generateExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3))
    }
}