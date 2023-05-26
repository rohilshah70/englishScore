package com.dragos.ageguessing

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual fun getClientEngine(): HttpClientEngine = OkHttp.create()