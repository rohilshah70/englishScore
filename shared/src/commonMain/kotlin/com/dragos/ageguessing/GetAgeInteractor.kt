package com.dragos.ageguessing

class GetAgeInteractor {
    private val networkManager: NetworkManager = NetworkManager()

    suspend fun getAge(name: String): AgeDetails? = networkManager.getAge(name)

}