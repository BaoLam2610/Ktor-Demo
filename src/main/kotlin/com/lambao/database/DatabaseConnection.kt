package com.lambao.database

import org.ktorm.database.Database

object DatabaseConnection {

    private const val DB_NAME = "user" // note

    val instance = Database.connect(
        url = "jdbc:mysql://localhost:3306/$DB_NAME",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "admin123"
    )
}