package com.zeni.auth.presentation.register.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.zeni.auth.domain.utils.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.zeni.auth.domain.utils.RegisterErrors


@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    val username = MutableStateFlow("")
    fun setUsername(value: String) {
        username.value = value
    }

    val password = MutableStateFlow("")
    fun setPassword(value: String) {
        password.value = value
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    sendEmailVerification()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
    }

    fun recoverPassword(email: String) {
        if (email.isEmpty()) {
            _authState.value = AuthState.Error("El correo no puede estar vacío")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Idle // O un estado de éxito
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Error al enviar el correo de recuperación")
                }
            }
    }

    val registerError: StateFlow<RegisterErrors?>
        field = MutableStateFlow(value = null)
    fun verifyCredentials(): Boolean {
        TODO("Implement registration handling, and show errors in registerErrors")
    }
}