package com.dragos.ageguessing.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragos.ageguessing.GetAgeInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
* @created 01/06/23 - 6:17 PM
* @project Zoomcar
* @author Rohil
* Copyright (c) 2022 Zoomcar. All rights reserved.
*/

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AgingState())
    val uiState: StateFlow<AgingState> = _uiState.asStateFlow()


    fun getAge(speechToText: String?) {
        var age: Int? = null
        val items = speechToText?.split(" ")
        viewModelScope.launch(Dispatchers.IO) {
            val runningTasks = items?.map { text ->
                async {
                    val apiResponse = GetAgeInteractor().getAge(text)
                    text to apiResponse
                }
            }
            val responses = runningTasks?.awaitAll()
            responses?.forEach { (id, response) ->
                response?.let {
                    age = it.age
                }
            }

            _uiState.update { currentState ->
                currentState.copy(
                    age = age,
                    showError = age == null
                )
            }
        }
    }

}

data class AgingState(
    val age: Int? = null,
    val showLoader: Boolean = false,
    val showError: Boolean = false
)