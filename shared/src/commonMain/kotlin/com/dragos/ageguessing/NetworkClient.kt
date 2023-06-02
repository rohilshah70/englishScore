package com.dragos.ageguessing

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

expect fun getClientEngine(): HttpClientEngine

data class AgeDetails(val name: String, val age: Int)

@kotlinx.serialization.Serializable
data class AgeResponse(val name: String?, val age: Int?)

class NetworkManager {
    private val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }
    }

    val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun getAge(name: String): AgeDetails? {
        return try {
            val request = request {
                url("https://api.agify.io/")
                parameter("name", name)
            }
            val response: String = client.get(request).body()

            if (response == "null") {
                println(Error("Age cannot be determined"))
                null
            } else {
                val ageResponse = json.decodeFromString<AgeResponse>(response)
                if (ageResponse.name == null || ageResponse.age == null) {
                    println(Error("Invalid response format"))
                    null
                } else {
                    val age = getAge(ageResponse.age.toInt())
                    AgeDetails(ageResponse.name, age)
                }
            }
        } catch (e: ResponseException) {
            println(Error("HTTP error: ${e.response.status.value}"))
            null
        } catch (e: Exception) {
            println(Error("Unknown error: ${e.message}"))
            null
        }
    }
}