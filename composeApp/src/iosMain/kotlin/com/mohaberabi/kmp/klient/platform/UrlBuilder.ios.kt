package com.mohaberabi.kmp.klient.platform

import platform.Foundation.*

actual fun buildUrl(
    url: String,
    params: Map<String, Any?>
): String {
    val components = NSURLComponents.componentsWithString(url) ?: return url

    if (params.isNotEmpty()) {
        val queryItems = params.map { (key, value) ->
            NSURLQueryItem(name = key, value = value?.toString())
        }
        components.queryItems = queryItems
    }

    return components.URL?.absoluteString ?: url
}