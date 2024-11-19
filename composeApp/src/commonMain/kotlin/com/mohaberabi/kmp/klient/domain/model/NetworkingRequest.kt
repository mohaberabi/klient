package com.mohaberabi.kmp.klient.domain.model


data class NetworkingRequest(
    val url: String,
    val method: NetworkMethod,
    val headers: Map<String, String> = mapOf(),
    val body: String? = null,
    val params: Map<String, Any> = mapOf()
)
