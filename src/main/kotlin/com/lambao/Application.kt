package com.lambao

import com.lambao.config.configureUserDB
import com.lambao.manager.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        extracted()
    }
        .start(wait = true)
}

private fun Application.extracted() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val tokenManger = TokenManager(config)

    install(Authentication) {
        jwt {
            verifier(tokenManger.verifyJwtToken())
            realm = config.property("realm").getString()
            validate { jwtCredential ->
                if(jwtCredential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(jwtCredential.payload)
                } else {
                    null
                }
            }
        }
    }

    install(ContentNegotiation) {
        json()
    }

    module()
}

fun Application.module() {
    routing {
//        configureRouting()
//        configureUser()
//        configureNote()
        configureUserDB()
    }
}

