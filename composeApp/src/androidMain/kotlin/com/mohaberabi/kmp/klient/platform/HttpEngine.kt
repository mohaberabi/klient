package com.mohaberabi.kmp.klient.platform

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingConfiguration
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class HttpEngine(
    private val config: NetworkingConfiguration
) {
    actual suspend fun makeRequest(request: NetworkingRequest): NetworkResponse {
        return suspendCancellableCoroutine { continuation ->
            try {
                val method = request.method
                val headers = request.headers
                val body = request.body
                val url = request.url
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.apply {
                    requestMethod = method.name
                    connectTimeout = config.connectionTimeOut
                    readTimeout = config.readTimeOut
                    headers.forEach { (key, value) ->
                        addRequestProperty(key, value)
                        if (body != null && method.shouldAddBody) {
                            doOutput = true
                            val encodedBody = body.encodeToByteArray()
                            outputStream.use { stream ->
                                stream.write(encodedBody)
                            }
                        }
                    }
                }

                val code = connection.responseCode
                val responseBody = connection.inputStream.bufferedReader().use { it.readText() }
                val responseHeaders = connection.headerFields.filterKeys { it != null }
                connection.disconnect()
                val response = NetworkResponse(
                    statusCode = code,
                    body = responseBody,
                    headers = responseHeaders.mapValues { it.value.joinToString { ", " } },
                )
                continuation.resume(response)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                e.printStackTrace()
                continuation.resumeWithException(e)
            }

        }


    }


}