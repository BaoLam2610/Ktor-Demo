package com.lambao.model.entity

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object NoteEntity : Table<Nothing>(tableName = "note") {
    val id = int("id").primaryKey()
    val title = varchar("title")
}