package com.mohaberabi.kmp.klient

import com.mohaberabi.kmp.klient.data.PostDto
import com.mohaberabi.kmp.klient.platform.safeGet
import com.mohaberabi.kmp.klient.platform.toJsonObject


actual fun String.toPostDto(): PostDto {
    val json = toJsonObject()
    val title = json.safeGet("unknown") { getString("title") }
    val body = json.safeGet("unknown") { getString("body") }
    val userId = json.safeGet(1) { getInt("userId") }
    return PostDto(title, body, userId)
}