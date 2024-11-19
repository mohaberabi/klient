package com.mohaberabi.kmp.klient.domain.source

import com.mohaberabi.kmp.klient.domain.model.PostModel

interface ApiPlayGround {

    suspend fun getPost(
        id: Int
    ): Result<PostModel>

    suspend fun addPost(
        post: PostModel
    ): Result<Unit>

    suspend fun deletePost(
        id: Int
    ): Result<Unit>
}