package com.mohaberabi.kmp.klient.platform

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest

expect class HttpEngine {
    suspend fun makeRequest(
        request: NetworkingRequest,
    ): NetworkResponse

}