package com.mohaberabi.kmp.klient.domain.source

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse

interface Networking {


    suspend fun get(
        url: String,
        params: Map<String, Any> = mapOf(),
        headers: Map<String, String> = mapOf()
    ): NetworkResponse


    suspend fun post(
        url: String,
        headers: Map<String, String> = mapOf(),
        body: String? = null
    ): NetworkResponse

    suspend fun put(
        url: String,
        headers: Map<String, String> = mapOf(),
        body: String? = null
    ): NetworkResponse

    suspend fun delete(
        url: String,
        headers: Map<String, String> = mapOf(),
        queryParams: Map<String, Any> = emptyMap()
    ): NetworkResponse
}