package com.zeni.auth.presentation.register.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.auth.domain.utils.LoginErrors
import com.zeni.auth.domain.utils.RegisterErrors
import com.zeni.auth.presentation.login.components.LoginViewModel.DefaultCredentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

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

    val registerError: StateFlow<RegisterErrors?>
        field = MutableStateFlow(value = null)
    fun verifyCredentials(): Boolean {
        TODO("Implement registration handling, and show errors in registerErrors")
    }
}