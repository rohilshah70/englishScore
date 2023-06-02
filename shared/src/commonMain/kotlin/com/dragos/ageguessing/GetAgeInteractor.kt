package com.dragos.ageguessing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAgeInteractor {
    private val networkManager: NetworkManager = NetworkManager()

    suspend fun getAge(name: String): AgeDetails? = networkManager.getAge(name)

}