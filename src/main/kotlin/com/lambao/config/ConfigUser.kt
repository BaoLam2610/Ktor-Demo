package com.lambao.config

import com.lambao.model.DataResponse
import com.lambao.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File


fun Routing.configureUser() {
    get("/user") {
        println("URI: ${call.request.uri}")
        println("Header: ${call.request.headers.names()}")
        println("query param: ${call.request.queryParameters.names()}")
        println("email param: ${call.request.queryParameters["email"]}")
        println("password param: ${call.request.queryParameters["password"]}")
//        call.respondText("Hi")
        call.respondText("Something went wrong", status = HttpStatusCode.BadGateway)
    }

    get("/users/{page}") {
        val page = call.parameters["page"] ?: return@get

        call.respondText("Page: $page")
    }

    post("/login") {
        val user = call.receive<User>()
        println(user)
        call.respond(DataResponse("OKK", user))
    }

    get("/header") {
        call.response.headers.append("token", "DemoToken")
    }

    get("/file-download") {
        val file = File("files/shinobu-koucho.jpg")

        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Attachment.withParameter(
                ContentDisposition.Parameters.FileName,
                "demo-download.jpg"
            ).toString()
        )

        call.respondFile(file) {
            DataResponse("Downloading..", null)
        }
    }

    get("/file-open") {
        val file = File("files/shinobu-koucho.jpg")

        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Inline.withParameter(
                ContentDisposition.Parameters.FileName,
                "demo-open.jpg"
            ).toString()
        )

        call.respondFile(file) {
            println(this)
            DataResponse("OpenFile", null)
        }
    }
}