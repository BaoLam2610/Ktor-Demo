package com.lambao.config

import com.lambao.model.DataResponse
import com.lambao.database.UserDao
import com.lambao.manager.TokenManager
import com.lambao.model.User
import com.lambao.model.UserToken
import com.lambao.model.request.UserRequest
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.configureUserDB() {
    val dao = UserDao()

    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    get("/users") {
        val users = dao.getUsers()
        call.respond(DataResponse("OK", users))
    }

    post("/register") {
        val request = call.receive<UserRequest>()

        if (!request.isUsernameValid()) {
            call.respond(
                HttpStatusCode.BadRequest,
                DataResponse("username at leash 3 digit", data = null)
            )
            return@post
        }

        if (!request.isPasswordValid()) {
            call.respond(
                HttpStatusCode.BadRequest,
                DataResponse("password at leash 6 digit", data = null)
            )
            return@post
        }

        if (dao.isExistsUsername(request.username ?: return@post)) {
            call.respond(
                HttpStatusCode.BadRequest,
                DataResponse("User is available", data = null)
            )
            return@post
        }

        val query = dao.insertUser(request)
        call.respond(
            if (query == 1) DataResponse("OK", dao.getUsers())
            else DataResponse("Wrong to register", data = null)
        )
    }

    post("/login") {
        val request = call.receive<UserRequest>()

        if (!request.isUsernameValid()) {
            call.respond(
                HttpStatusCode.BadRequest,
                DataResponse("username at leash 3 digit", data = null)
            )
            return@post
        }

        if (!request.isPasswordValid()) {
            call.respond(
                HttpStatusCode.BadRequest,
                DataResponse("password at leash 6 digit", data = null)
            )
            return@post
        }

        val user = dao.getUserTokenByName(request.username!!)

        if (user != null) {

            val token = tokenManager.generateJwtToken(user)

            call.respond(
                DataResponse("Login success", data = user.apply { this.token = token })
            )
            return@post
        }

        call.respond(
            HttpStatusCode.BadRequest,
            DataResponse("Wrong username or password, please try again", data = null)
        )
    }

    authenticate {
        get("/user/info") {
            val principle = call.principal<JWTPrincipal>()
            val username = principle!!.payload.getClaim("username").asString()
            val id = principle.payload.getClaim("id").asInt()

            call.respond(DataResponse("OKK", UserToken(id, username, null)))
        }
    }
}