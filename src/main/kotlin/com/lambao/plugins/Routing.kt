package com.lambao.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.configureRouting() {
    get("/") {
        call.respondText("Hello World!")
    }
}
