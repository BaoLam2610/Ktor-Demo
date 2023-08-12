package com.lambao.config

import com.lambao.model.DataResponse
import com.lambao.database.*
import com.lambao.model.request.NoteRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.configureNote() {
    val db = DatabaseConnection
    get("/notes") {
        val notes = db.fetchNote()
        call.respond(DataResponse("OK", notes))
    }

    get("/note") {
        val request = call.parameters
        val note = db.findNoteById(request["id"]?.toIntOrNull())
        call.respond(
            if (note != null) DataResponse("OK", note)
            else DataResponse("Cannot find note in id: ${request["id"]}", data = null)
        )
    }

    post("/note/create") {
        val request = call.receive<NoteRequest>()
        val query = db.createNote(request)
        call.respond(
            if (query == 1) DataResponse("OK", db.fetchNote())
            else DataResponse("Wrong", data = null)
        )
    }

    post("/note/edit") {
        val request = call.receive<NoteRequest>()
        val query = db.updateNote(request)
        call.respond(
            if (query >= 1) DataResponse("OK", db.fetchNote())
            else DataResponse("Cannot find note to edit", data = null)
        )
    }

    post("/note/delete") {
        val request = call.parameters
        val query = db.deleteNote(request["id"]?.toIntOrNull() ?: -1)
        call.respond(
            if (query >= 1) DataResponse("OK", db.fetchNote())
            else DataResponse("Cannot find note to delete", data = null)
        )
    }
}