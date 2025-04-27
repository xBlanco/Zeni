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
import com.zeni.R

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _saveState = MutableStateFlow<SaveState>(SaveState.Idle)
    val saveState: StateFlow<SaveState> = _saveState

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

            // Verificar si el nombre de usuario ya está en uso
            val isUsernameTaken = userRepository.isUsernameTaken(username.value)
            val existingUser = userRepository.getUserByLogin(login)

            if (isUsernameTaken && (existingUser == null || existingUser.username != username.value)) {
                usernameError.value = true
                _saveState.value = SaveState.Error(R.string.error_username_taken)
                return@launch
            }

            if (existingUser != null) {
                // Actualizar los datos del usuario
                val updatedUser = existingUser.copy(
                    username = username.value,
                    birthdate = birthdate.value,
                    address = address.value,
                    country = country.value,
                    phoneNumber = phoneNumber.value,
                    acceptEmails = acceptEmails.value
                )
                userRepository.saveUser(updatedUser)
                _saveState.value = SaveState.Success(R.string.success_user_updated)
            } else {
                // Crear un nuevo usuario
                val newUser = UserEntity(
                    login = login,
                    username = username.value,
                    birthdate = birthdate.value,
                    address = address.value,
                    country = country.value,
                    phoneNumber = phoneNumber.value,
                    acceptEmails = acceptEmails.value
                )
                userRepository.saveUser(newUser)
                _saveState.value = SaveState.Success(R.string.success_user_created)            }
        }
    }

    fun resetSaveState() {
        _saveState.value = SaveState.Idle
    }

    sealed class SaveState {
        object Idle : SaveState()
        data class Success(val message: Int) : SaveState()
        data class Error(val message: Int) : SaveState()
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