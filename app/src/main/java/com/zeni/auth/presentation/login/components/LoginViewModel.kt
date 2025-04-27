package com.zeni.auth.presentation.login.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.zeni.auth.domain.use_cases.LoginUseCase
import com.zeni.auth.domain.utils.AuthState
import com.zeni.auth.domain.utils.LoginErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    val username = MutableStateFlow("")
    fun setUsername(value: String) {
        viewModelScope.launch {
            username.value = value
            loginError.value = null
        }
    }
    val password = MutableStateFlow("")
    fun setPassword(value: String) {
        viewModelScope.launch {
            password.value = value
            loginError.value = null
        }
    }
    val loginError = MutableStateFlow<LoginErrors?>(null)

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthState.Error("Invalid email format")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        _authState.value = AuthState.Authenticated
                    } else {
                        _authState.value = AuthState.Error("Email not verified. Please verify your email.")
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Invalid email or password"
                    if (_authState.value == AuthState.Loading) {
                        _authState.value = AuthState.Error(errorMessage)
                    }
                }
            }
    }


//    fun verifyCredentials(): Boolean {
//        // TODO: Implement a real authentication mechanism with backend
//        val isValidCredentials = username.value == DefaultCredentials.USERNAME &&
//                password.value == DefaultCredentials.PASSWORD
//
//        // TODO: Plan if needed to show exactly where is the error
//        if (!isValidCredentials) {
//            viewModelScope.launch {
//                loginError.emit(LoginErrors.INVALID_CREDENTIALS)
//            }
//        }
//
//        return isValidCredentials
//    }
//
//    private object DefaultCredentials {
//        const val USERNAME = "user"
//        const val PASSWORD = "1234"
//    }
}