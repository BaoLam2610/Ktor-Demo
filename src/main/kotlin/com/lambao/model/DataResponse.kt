package com.lambao.model

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(
    val message: String?,
    val data: T?
)