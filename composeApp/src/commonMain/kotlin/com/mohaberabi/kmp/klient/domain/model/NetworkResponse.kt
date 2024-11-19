package com.mohaberabi.kmp.klient.domain.model

data class NetworkResponse(
    val statusCode: Int,
    val headers: Map<String, String>,
    val body: String?,
)
