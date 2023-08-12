package com.lambao.database

import com.lambao.model.Note
import com.lambao.model.entity.NoteEntity
import com.lambao.model.request.NoteRequest
import org.ktorm.database.Database
import org.ktorm.dsl.*

fun connectToMySql() {
//    val database = Database.connect(
//        url = "jdbc:mysql://localhost:3306/note",
//        driver = "com.mysql.cj.jdbc.Driver",
//        user = "root",
//        password = "admin123"
//    )
//    database.insertNote()
//    database.updateNote()
//    database.deleteNote()
//    database.selectNote()
}

fun Database.insertNote() {
    for (i in 1..2) {
        this.insert(NoteEntity) {
            set(it.title, "Android Java Tutorial $i")
        }
    }

    for (i in 1..2) {
        this.insert(NoteEntity) {
            set(it.title, "Android Kotlin Tutorial $i")
        }
    }
}

fun Database.selectNote() {
    val notes = this.from(NoteEntity).select()
//        .where {
//            (NoteEntity.title like "%Android%") and (NoteEntity.title like "%2")
//        }
    for (row in notes) {
        println("${row[NoteEntity.id]} - ${row[NoteEntity.title]}")
    }
}

fun Database.updateNote() {
    this.update(NoteEntity) {
        set(it.title, "Android Tutorial")
        where {
            (NoteEntity.id eq 1)
        }
    }
}

fun Database.deleteNote() {
    this.delete(NoteEntity) {
        it.id notEq  1
    }
}

fun Query.mapNote() = map {
    Note(it[NoteEntity.id], it[NoteEntity.title])
}

fun DatabaseConnection.getAllNote() = instance.from(NoteEntity).select()

fun DatabaseConnection.fetchNote() =
    getAllNote().mapNote()

fun DatabaseConnection.findNoteById(id: Int?) =
    getAllNote().where {
        NoteEntity.id eq (id ?: -1)
    }.mapNote().firstOrNull()

fun DatabaseConnection.createNote(request: NoteRequest) =
    instance.insert(NoteEntity) {
        set(it.title, request.title)
    }

fun DatabaseConnection.updateNote(request: NoteRequest) =
    instance.update(NoteEntity) {
        set(it.title, request.title)
        where { it.id eq (request.id ?: -1) }
    }

fun DatabaseConnection.deleteNote(id: Int) =
    instance.delete(NoteEntity) {
        it.id eq id
    }


