package com.zeni.login.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val username: StateFlow<String>
        field = MutableStateFlow(value = "")
    fun setUsername(value: String) {
        viewModelScope.launch {
            username.emit(value)
        }
    }

    val password: StateFlow<String>
        field = MutableStateFlow(value = "")
    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
        }
    }

    fun verifyCredentials(): Boolean {
        // TODO: Implement a real authentication mechanism with backend
        return username.value == DefaultCredentials.USERNAME &&
                password.value == DefaultCredentials.PASSWORD
    }

    private object DefaultCredentials {
        const val USERNAME = "user"
        const val PASSWORD = "1234"
    }
}