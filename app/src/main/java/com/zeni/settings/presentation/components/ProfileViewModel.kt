package com.zeni.settings.presentation.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.zeni.auth.domain.utils.AuthState
import com.zeni.settings.domain.model.UserEntity
import com.zeni.settings.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    val username = MutableStateFlow("")
    val birthdate = MutableStateFlow("")
    val address = MutableStateFlow("")
    val country = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")
    val acceptEmails = MutableStateFlow(false)
    val usernameError = MutableStateFlow(false)

    fun setUsername(value: String) {
        username.value = value
        usernameError.value = false
    }

    fun setBirthdate(value: String) {
        birthdate.value = value
    }

    fun setAddress(value: String) {
        address.value = value
    }

    fun setCountry(value: String) {
        country.value = value
    }

    fun setPhoneNumber(value: String) {
        phoneNumber.value = value
    }

    fun setAcceptEmails(value: Boolean) {
        acceptEmails.value = value
    }

    fun saveUserInfo() {
        viewModelScope.launch {
            val login = FirebaseAuth.getInstance().currentUser?.email ?: return@launch

            // Eliminar el usuario existente con el mismo login
            userRepository.deleteUserByLogin(login)

            // Crear e insertar el nuevo usuario
            val user = UserEntity(
                login = login,
                username = username.value,
                birthdate = birthdate.value,
                address = address.value,
                country = country.value,
                phoneNumber = phoneNumber.value,
                acceptEmails = acceptEmails.value
            )
            userRepository.saveUser(user)
        }
    }

    fun loadUserInfo(login: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByLogin(login)
            if (user != null) {
                username.value = user.username
                birthdate.value = user.birthdate
                address.value = user.address
                country.value = user.country
                phoneNumber.value = user.phoneNumber
                acceptEmails.value = user.acceptEmails
                Log.d("ProfileViewModel", "Datos cargados correctamente: $user")
            } else {
                Log.d("ProfileViewModel", "No se encontró un usuario con el login: $login")
            }
        }
    }

    fun changeProfile() {
        TODO("Function to change some profile data")
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}