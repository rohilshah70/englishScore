package com.dragos.ageguessing

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual fun getClientEngine(): HttpClientEngine = Darwin.create()