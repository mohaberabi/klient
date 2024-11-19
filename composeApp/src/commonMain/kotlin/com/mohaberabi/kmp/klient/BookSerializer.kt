package com.mohaberabi.kmp.klient

import com.mohaberabi.kmp.klient.data.BookDto
import com.mohaberabi.kmp.klient.data.PostDto


expect fun String.toBookDto(): BookDto


expect fun String.toPostDto(): PostDto





