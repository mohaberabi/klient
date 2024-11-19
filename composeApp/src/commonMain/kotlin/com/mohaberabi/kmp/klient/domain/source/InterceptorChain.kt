package com.mohaberabi.kmp.klient.domain.source

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest

class InterceptorChain(
    private val interceptors: List<NetworkInterceptor>,
    private val index: Int,
    private val onReady: suspend (NetworkingRequest) -> NetworkResponse
) {

    suspend fun proceed(
        request: NetworkingRequest
    ): NetworkResponse {
        return if (index >= interceptors.size) {
            onReady(request)
        } else {
            val nextChain = InterceptorChain(
                interceptors = interceptors,
                index = index + 1,
                onReady = onReady
            )
            val nextInterceptor = interceptors[index]
            return nextInterceptor.intercept(request, nextChain)
        }

    }
}
