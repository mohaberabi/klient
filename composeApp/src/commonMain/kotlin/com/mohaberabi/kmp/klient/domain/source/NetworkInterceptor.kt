package com.mohaberabi.kmp.klient.domain.source

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest

interface NetworkInterceptor {
    suspend fun intercept(
        request: NetworkingRequest,
        chain: InterceptorChain,
    ): NetworkResponse
}


