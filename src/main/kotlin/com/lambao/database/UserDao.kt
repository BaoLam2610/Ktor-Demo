package com.lambao.database

import com.lambao.model.User
import com.lambao.model.UserToken
import com.lambao.model.entity.UserEntity
import com.lambao.model.request.UserRequest
import org.ktorm.dsl.*

class UserDao {

    private val db = DatabaseConnection.instance

    private fun Query.mapUser() = map { User(it[UserEntity.username], it[UserEntity.password]) }

    private fun Query.mapUserToken() = map {
        UserToken(it[UserEntity.id], it[UserEntity.username], it[UserEntity.password])
    }

    private fun select() = db.from(UserEntity).select()

    fun insertUser(request: UserRequest) =
        db.insert(UserEntity) {
            set(it.username, request.username)
            set(it.password, request.password)
        }

    fun getUsers() = select().mapUser()

    fun getUserById(id: Int) =
        select().where { UserEntity.id eq id }.mapUser().firstOrNull()

    fun getUserByName(name: String) =
        select().where { UserEntity.username eq name }.mapUser().firstOrNull()

    fun getUserTokenByName(name: String) =
        select().where { UserEntity.username eq name }.mapUserToken().firstOrNull()

    fun isExistsUsername(username: String) = getUserByName(username) != null

    fun isExistsUser(request: UserRequest): Boolean {
        return select().where {
            (UserEntity.username eq request.username!!) and
                    (UserEntity.password like request.password!!)
        }.mapUser().firstOrNull() != null
    }
}