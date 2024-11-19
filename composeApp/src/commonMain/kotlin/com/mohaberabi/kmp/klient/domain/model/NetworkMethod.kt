package com.mohaberabi.kmp.klient.domain.model

enum class NetworkMethod {
    GET,
    DELETE,
    PUT,
    POST;

    val shouldAddBody: Boolean get() = this == POST || this == PUT
}