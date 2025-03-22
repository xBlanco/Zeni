package com.zeni

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zeni.core.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    var isManualDarkTheme by mutableStateOf(sharedPrefsManager.autoDarkTheme)
        private set

    var isDarkTheme by mutableStateOf(sharedPrefsManager.darkTheme)
        private set
}