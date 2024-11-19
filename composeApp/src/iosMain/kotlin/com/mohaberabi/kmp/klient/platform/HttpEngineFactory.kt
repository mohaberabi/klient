package com.mohaberabi.kmp.klient.platform

import com.mohaberabi.kmp.klient.domain.model.NetworkingConfiguration

actual class HttpEngineFactory {
    actual fun create(
        config: NetworkingConfiguration,
    ): HttpEngine = HttpEngine(config)


}