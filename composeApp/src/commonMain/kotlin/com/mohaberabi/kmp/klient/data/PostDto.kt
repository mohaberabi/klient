package com.mohaberabi.kmp.klient.data

import com.mohaberabi.kmp.klient.domain.model.PostModel
import com.mohaberabi.kmp.klient.platform.toJsonString


data class PostDto(
    val title: String,
    val body: String,
    val userId: Int
)

fun PostDto.toPost() = PostModel(
    title, body, userId
)

fun PostModel.toPostRequestDto() = PostDto(
    title, body, userId
)

fun PostDto.toJson() = mapOf(
    "title" to title,
    "body" to body,
    "userId" to userId
).toJsonString()