package com.mohaberabi.kmp.klient.platform

import com.mohaberabi.kmp.klient.domain.model.NetworkingConfiguration

expect class HttpEngineFactory() {
    fun create(
        config: NetworkingConfiguration,
    ): HttpEngine
}