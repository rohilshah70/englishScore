package com.dragos.ageguessing.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragos.ageguessing.GetAgeInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AgingState())
    val uiState: StateFlow<AgingState> = _uiState.asStateFlow()


    fun getAge(speechToText: String?) {
        var age: Int? = null
        val items = speechToText?.split(" ")
        viewModelScope.launch(Dispatchers.IO) {
            val runningTasks = items?.map { text ->
                async {
                    val apiResponse = GetAgeInteractor().getAge(text).first()
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
                    showError = age == null,
                    showConfetti = true
                )
            }
            delay(5000)
            _uiState.update { currentState ->
                currentState.copy(
                    showConfetti = false
                )
            }

        }
    }

}

data class AgingState(
    val age: Int? = null,
    val showError: Boolean = false,
    val showConfetti: Boolean = false,
)