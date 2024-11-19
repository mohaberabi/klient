package com.mohaberabi.kmp.klient.platform

actual fun buildUrl(
    url: String,
    params: Map<String, Any?>
): String {
    if (params.isEmpty()) return url
    val encodedParams = params.map { (key, value) ->
        "${key}=${value.toString().urlEncode()}"
    }.joinToString("&")
    return "$url?$encodedParams"

}

fun String.urlEncode(): String = java.net.URLEncoder.encode(this, "UTF-8")
