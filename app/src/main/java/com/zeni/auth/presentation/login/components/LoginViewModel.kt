package com.zeni.auth.presentation.login.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.auth.domain.utils.LoginErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val username: StateFlow<String>
        field = MutableStateFlow(value = "")
    fun setUsername(value: String) {
        viewModelScope.launch {
            username.emit(value)
            loginError.emit(null)
        }
    }

    val password: StateFlow<String>
        field = MutableStateFlow(value = "")
    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
            loginError.emit(null)
        }
    }

    val loginError: StateFlow<LoginErrors?>
        field = MutableStateFlow(value = null)
    fun verifyCredentials(): Boolean {
        // TODO: Implement a real authentication mechanism with backend
        val isValidCredentials = username.value == DefaultCredentials.USERNAME &&
                password.value == DefaultCredentials.PASSWORD

        // TODO: Plan if needed to show exactly where is the error
        if (!isValidCredentials) {
            viewModelScope.launch {
                loginError.emit(LoginErrors.INVALID_CREDENTIALS)
            }
        }

        return isValidCredentials
    }

    private object DefaultCredentials {
        const val USERNAME = "user"
        const val PASSWORD = "1234"
    }
}