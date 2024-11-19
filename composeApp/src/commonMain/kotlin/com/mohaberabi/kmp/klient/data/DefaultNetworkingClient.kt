package com.mohaberabi.kmp.klient.data

import com.mohaberabi.kmp.klient.platform.HttpEngine
import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest
import com.mohaberabi.kmp.klient.domain.source.InterceptorChain
import com.mohaberabi.kmp.klient.domain.source.NetworkClient
import com.mohaberabi.kmp.klient.domain.source.NetworkInterceptor

class DefaultNetworkingClient(
    private val engine: HttpEngine
) : NetworkClient {

    private val interceptors = mutableListOf<NetworkInterceptor>()
    override fun addInterceptor(interceptor: NetworkInterceptor) {
        interceptors.add(interceptor)
    }

    override suspend fun makeRequest(request: NetworkingRequest): NetworkResponse {
        val chain = InterceptorChain(
            interceptors = interceptors,
            index = 0,
            onReady = { engine.makeRequest(request) })
        return chain.proceed(request)

    }
}