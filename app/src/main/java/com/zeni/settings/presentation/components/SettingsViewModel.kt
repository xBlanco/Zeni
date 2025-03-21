package com.zeni.settings.presentation.components

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zeni.core.data.SharedPrefsManager
import com.zeni.settings.domain.utils.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    var language by mutableStateOf(sharedPrefsManager.language ?: Languages.SPANISH)
        private set

    var isAutoDarkTheme by mutableStateOf(sharedPrefsManager.autoDarkTheme)
        private set

    var isDarkTheme by mutableStateOf(sharedPrefsManager.darkTheme)
        private set

    fun updateLanguage(newLanguage: Languages) {
        sharedPrefsManager.language = newLanguage
        language = newLanguage
    }

    fun updateAutoDarkTheme(isManualDark: Boolean) {
        sharedPrefsManager.autoDarkTheme = isManualDark
        isAutoDarkTheme = isManualDark
    }

    fun updateDarkTheme(context: Context, isDark: Boolean) {
        sharedPrefsManager.setDarkTheme(context, isDark)
        isDarkTheme = isDark
    }
}