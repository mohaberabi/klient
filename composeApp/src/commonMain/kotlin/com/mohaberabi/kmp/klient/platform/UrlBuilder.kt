package com.mohaberabi.kmp.klient.platform

expect fun buildUrl(
    url: String,
    params: Map<String, Any?> = mapOf(),
): String