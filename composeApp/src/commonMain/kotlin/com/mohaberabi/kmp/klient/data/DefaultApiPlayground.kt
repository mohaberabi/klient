package com.mohaberabi.kmp.klient.data

import com.mohaberabi.kmp.klient.domain.model.PostModel
import com.mohaberabi.kmp.klient.domain.source.ApiPlayGround
import com.mohaberabi.kmp.klient.domain.source.Networking
import com.mohaberabi.kmp.klient.toBookDto
import com.mohaberabi.kmp.klient.toPostDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class DefaultApiPlayground(
    private val networking: Networking,
) : ApiPlayGround {


    override suspend fun getPost(id: Int): Result<PostModel> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val response = networking.get("https://jsonplaceholder.typicode.com/posts/$id")
                val body = response.body ?: throw Exception("Response is null")
                val postDto = body.toPostDto()
                val post = postDto.toPost()
                post
            }.onFailure {
                if (it is CancellationException) {
                    throw it
                }
            }
        }
    }

    override suspend fun addPost(post: PostModel): Result<Unit> {

        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val response = networking.post(
                    "https://jsonplaceholder.typicode.com/posts",
                    body = post.toPostRequestDto().toJson()
                )
            }.onFailure {
                if (it is CancellationException) {
                    throw it
                }
            }
        }
    }

    override suspend fun deletePost(id: Int): Result<Unit> {
        return kotlin.runCatching {
            val response = networking.delete(
                "https://jsonplaceholder.typicode.com/posts/${id}",
            )
        }.onFailure {
            if (it is CancellationException) {
                throw it
            }
        }

    }
}


