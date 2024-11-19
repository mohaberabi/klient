package com.mohaberabi.kmp.klient.domain.source

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest


interface NetworkClient {
    suspend fun makeRequest(
        request: NetworkingRequest,
    ): NetworkResponse

    fun addInterceptor(
        interceptor: NetworkInterceptor,
    )
}