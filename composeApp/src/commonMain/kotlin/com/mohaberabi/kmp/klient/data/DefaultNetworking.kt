package com.mohaberabi.kmp.klient.data

import com.mohaberabi.kmp.klient.platform.buildUrl
import com.mohaberabi.kmp.klient.domain.model.NetworkMethod
import com.mohaberabi.kmp.klient.domain.model.NetworkResponse
import com.mohaberabi.kmp.klient.domain.model.NetworkingRequest
import com.mohaberabi.kmp.klient.domain.source.NetworkClient
import com.mohaberabi.kmp.klient.domain.source.Networking


class DefaultNetworking(
    private val client: NetworkClient,
) : Networking {
    override suspend fun get(
        url: String,
        params: Map<String, Any>,
        headers: Map<String, String>
    ): NetworkResponse {
        val fullUrl = buildUrl(url, params)
        val request = NetworkingRequest(
            method = NetworkMethod.GET,
            url = fullUrl,
            headers = headers
        )
        return client.makeRequest(request)
    }

    override suspend fun post(
        url: String,
        headers: Map<String, String>,
        body: String?,
    ): NetworkResponse {
        val fullUrl = buildUrl(url)
        val request = NetworkingRequest(
            method = NetworkMethod.POST,
            url = fullUrl,
            headers = headers,
            body = body
        )
        return client.makeRequest(request)
    }

    override suspend fun put(
        url: String,
        headers: Map<String, String>,
        body: String?,
    ): NetworkResponse {
        val fullUrl = buildUrl(url)
        val request = NetworkingRequest(
            method = NetworkMethod.PUT,
            url = fullUrl,
            headers = headers,
            body = body
        )
        return client.makeRequest(request)
    }

    override suspend fun delete(
        url: String,
        headers: Map<String, String>,
        queryParams: Map<String, Any>
    ): NetworkResponse {
        val fullUrl = buildUrl(url)
        val request = NetworkingRequest(
            method = NetworkMethod.DELETE,
            url = fullUrl,
            headers = headers,
        )
        return client.makeRequest(request)
    }


}