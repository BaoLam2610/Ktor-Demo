package com.lambao.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String?,
    val password: String?
)

@Serializable
data class UserToken(
    val id: Int?,
    val username: String?,
    val password: String?
) {
    var token: String? = null
}