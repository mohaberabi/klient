package com.mohaberabi.kmp.klient.data

import com.mohaberabi.kmp.klient.platform.AppLogger
import com.mohaberabi.kmp.klient.domain.model.ContentType
import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest
import com.mohaberabi.kmp.klient.domain.source.InterceptorChain
import com.mohaberabi.kmp.klient.domain.source.NetworkInterceptor

class LoggingInterceptor : NetworkInterceptor {
    override suspend fun intercept(
        request: NetworkingRequest,
        chain: InterceptorChain
    ): NetworkResponse {
        AppLogger.d("LoggingInterceptor", "REQUEST:Url->${request.url}")
        AppLogger.d("LoggingInterceptor", "REQUEST:Body->${request.body}")
        AppLogger.d("LoggingInterceptor", "REQUEST:Headers->${request.headers}")
        AppLogger.d("LoggingInterceptor", "REQUEST:Params->${request.params}")
        val response = chain.proceed(request)
        AppLogger.d("LoggingInterceptor", "RESPONSE:Status->${response.statusCode}")
        AppLogger.d("LoggingInterceptor", "RESPONSE:Headers->${response.headers}")
        AppLogger.d("LoggingInterceptor", "RESPONSE:Body->${response.body}")
        return response
    }
}

class DefaultInterceptor(
    private val type: ContentType
) : NetworkInterceptor {
    override suspend fun intercept(
        request: NetworkingRequest,
        chain: InterceptorChain
    ): NetworkResponse {
        val headers = request.headers.toMutableMap()
        headers["Content-Type"] = type.value
        val newRequest = request.copy(headers = headers.toMap())
        return chain.proceed(newRequest)
    }
}