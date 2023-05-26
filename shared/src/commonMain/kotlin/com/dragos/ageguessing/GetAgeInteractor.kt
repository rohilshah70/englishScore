package com.dragos.ageguessing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAgeInteractor {
    private val networkManager: NetworkManager = NetworkManager()

    fun getAge(name: String): Flow<AgeDetails> = flow {
        emit(networkManager.getAge(name).getOrThrow())
    }
}