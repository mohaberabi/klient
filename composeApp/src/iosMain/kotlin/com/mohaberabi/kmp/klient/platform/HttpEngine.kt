package com.mohaberabi.kmp.klient.platform

import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingConfiguration
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest
import com.mohaberabi.kmp.klient.utils.toNSData
import com.mohaberabi.kmp.klient.utils.toUtf8String
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.HTTPBody
import platform.Foundation.HTTPMethod
import platform.Foundation.NSHTTPURLResponse
import platform.Foundation.NSMutableURLRequest
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.dataTaskWithRequest
import platform.Foundation.setValue
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class HttpEngine(
    private val config: NetworkingConfiguration,
) {
    actual suspend fun makeRequest(request: NetworkingRequest): NetworkResponse {
        return suspendCancellableCoroutine { contintuation ->
            val url = request.url
            val nsUrl = NSURL.URLWithString(url)
                ?: run {
                    contintuation.resumeWithException(Exception("Url is badly formatted"))
                    return@suspendCancellableCoroutine
                }
            val nsRequest = NSMutableURLRequest.requestWithURL(nsUrl)
            nsRequest.apply {
                setTimeoutInterval((config.connectionTimeOut / 1000).toDouble())
                setAllowsCellularAccess(true)
                HTTPMethod = request.method.name
                request.headers.forEach { (key, value) ->
                    setValue(value = value, forHTTPHeaderField = key)
                }
                request.body?.let {
                    HTTPBody = request.body.encodeToByteArray().toNSData()
                }
            }
            val session = NSURLSession.sharedSession


            val task = session.dataTaskWithRequest(
                request = nsRequest,
                completionHandler = { data, response, error ->
                    if (error != null) {
                        contintuation.resumeWithException(
                            Throwable(
                                message = error.localizedDescription,
                                cause = Throwable(error.localizedFailureReason)
                            )
                        )
                        return@dataTaskWithRequest
                    }

                    val httpResponse = response as? NSHTTPURLResponse

                    val status = httpResponse?.statusCode?.toInt() ?: 500
                    val headers = httpResponse?.allHeaderFields?.entries?.associate {
                        it.key.toString() to it.value.toString()
                    }.orEmpty()
                    val body = data?.toUtf8String()
                    contintuation.resume(NetworkResponse(status, headers, body))
                },
            )
            task.resume()
        }


    }


}