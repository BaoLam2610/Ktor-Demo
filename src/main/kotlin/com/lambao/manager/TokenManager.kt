package com.lambao.manager

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.lambao.model.UserToken
import io.ktor.server.config.*
import java.util.*

class TokenManager(config: HoconApplicationConfig) {

    private val secret = config.property("secret").getString()
    private val issuer = config.property("issuer").getString()
    private val audience = config.property("audience").getString()

    fun generateJwtToken(user: UserToken): String {
        val expirationDate = System.currentTimeMillis() + 60_000

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withClaim("id", user.id)
            .withExpiresAt(Date(expirationDate))
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyJwtToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
    }
}