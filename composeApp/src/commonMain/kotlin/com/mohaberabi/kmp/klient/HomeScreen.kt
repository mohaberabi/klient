package com.mohaberabi.kmp.klient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mohaberabi.kmp.klient.data.DefaultInterceptor
import com.mohaberabi.kmp.klient.data.DefaultNetworking
import com.mohaberabi.kmp.klient.data.DefaultNetworkingClient
import com.mohaberabi.kmp.klient.data.LoggingInterceptor
import com.mohaberabi.kmp.klient.data.DefaultApiPlayground
import com.mohaberabi.kmp.klient.domain.model.ContentType
import com.mohaberabi.kmp.klient.domain.model.NetworkingConfiguration
import com.mohaberabi.kmp.klient.domain.model.PostModel
import com.mohaberabi.kmp.klient.platform.AppLogger
import com.mohaberabi.kmp.klient.platform.HttpEngineFactory
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val client = remember {
        DefaultNetworkingClient(
            HttpEngineFactory().create(
                NetworkingConfiguration(),
            )
        ).apply {
            addInterceptor(LoggingInterceptor())
            addInterceptor(DefaultInterceptor(ContentType.Json))
        }
    }
    val networking = remember {
        DefaultNetworking(client)
    }
    val source = remember {
        DefaultApiPlayground(networking)
    }
    var loading by remember {

        mutableStateOf(false)
    }
    var post by remember {
        mutableStateOf<PostModel?>(null)
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(
        key1 = Unit,
    ) {
        loading = true
        source.getPost(1)
            .onFailure {
                it.printStackTrace()
                loading = false
            }.onSuccess {
                post = it
                loading = false
            }

    }

    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                post?.let {
                    Text(text = "Post Found")
                    Text(
                        text = it.title,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                } ?: Text(text = "Something Went Wrong")
            }


            Button(
                onClick = {
                    scope.launch {
                        source.addPost(PostModel("test", "test", 1))
                            .onFailure {
                                it.printStackTrace()
                            }.onSuccess { AppLogger.d("post", "Post Added") }
                    }
                },
            ) {
                Text("Add Post Test")
            }
            Button(
                onClick = {
                    scope.launch {
                        source.deletePost(111)
                            .onFailure {
                                it.printStackTrace()
                            }.onSuccess { AppLogger.d("post", "Post Deleted") }
                    }
                },
            ) {
                Text("Delete Post Test")
            }
        }
    }
}