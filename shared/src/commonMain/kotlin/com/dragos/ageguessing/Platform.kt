package com.dragos.ageguessing

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform