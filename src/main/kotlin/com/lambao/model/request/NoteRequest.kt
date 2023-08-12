package com.lambao.model.request

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val id: Int?,
    val title: String?
)