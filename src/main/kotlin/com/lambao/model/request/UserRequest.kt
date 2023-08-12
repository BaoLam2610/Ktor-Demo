package com.lambao.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val username: String?,
    val password: String?
) {

    fun isUsernameValid() = (!username.isNullOrEmpty() && username.length >= 3)

    fun isPasswordValid() = (!password.isNullOrEmpty() && password.length >= 6)
}
