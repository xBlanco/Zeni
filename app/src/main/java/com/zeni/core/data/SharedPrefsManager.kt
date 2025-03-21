package com.zeni.core.data

import android.content.Context
import android.content.SharedPreferences
import com.zeni.core.domain.utils.LanguageChangeUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import com.zeni.core.data.SharedPrefsManager.PrefsKeys.DARK_THEME
import com.zeni.core.data.SharedPrefsManager.PrefsKeys.LANGUAGE
import com.zeni.settings.domain.utils.Languages

@Singleton
class SharedPrefsManager @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val preferences: SharedPreferences
) {
    val languageChangeUtil by lazy {
        LanguageChangeUtil()
    }

    var language: String?
        get() = preferences.getString(LANGUAGE, Languages.SPANISH.name)
        set(value) {
            preferences.edit { putString(LANGUAGE, value) }
            languageChangeUtil.changeLanguage(appContext, value ?: Languages.SPANISH.name)
        }

    var darkTheme: Boolean
        get() = preferences.getBoolean(DARK_THEME, false)
        set(value) = preferences.edit { putBoolean(DARK_THEME, value) }

    private object PrefsKeys {
        const val LANGUAGE = "language"
        const val DARK_THEME = "dark_theme"
    }
}
