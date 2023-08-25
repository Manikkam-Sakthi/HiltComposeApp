package com.example.sampleapp.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.models.Employee
import com.example.sampleapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val state = MutableStateFlow<State>(State.START)

    fun loadUser() = viewModelScope.launch {
        state.value = State.LOADING
        mainRepository.getUser().let {
            if (it.isSuccessful) {
                delay(2000)
                state.value = State.SUCCESS(it.body()?.data!!)
            } else {
                state.value = State.FAILURE("API Error")
            }
        }
    }

}

sealed class State {
    object START : State()
    object LOADING : State()
    data class SUCCESS(val users: List<Employee>) : State()
    data class FAILURE(val message: String) : State()
}