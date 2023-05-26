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

    suspend fun getAge(name: String): Result<AgeDetails> {
        return try {
            val request = request {
                url("https://api.agify.io/")
                parameter("name", name)
            }
            val response: String = client.get(request).body()

            if (response == "null") {
                Result.failure(Error("Age cannot be determined"))
            } else {

                val ageResponse = json.decodeFromString<AgeResponse>(response)
                if (ageResponse.name == null || ageResponse.age == null) {
                    Result.failure(Error("Invalid response format"))
                } else {
                    Result.success(AgeDetails(ageResponse.name, ageResponse.age.toInt()))
                }
            }
        } catch (e: ResponseException) {
            Result.failure(Error("HTTP error: ${e.response.status.value}"))
        } catch (e: Exception) {
            Result.failure(Error("Unknown error: ${e.message}"))
        }
    }
}