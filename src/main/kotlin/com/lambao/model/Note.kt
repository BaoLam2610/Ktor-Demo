package com.lambao.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int?,
    val name: String?
)
