package com.mohaberabi.kmp.klient.domain.model

data class NetworkingConfiguration(
    val connectionTimeOut: Int = 30 * 1000,
    val readTimeOut: Int = 30 * 1000,
    val contentType: ContentType = ContentType.Json
)
