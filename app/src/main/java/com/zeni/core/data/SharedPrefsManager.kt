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
import com.zeni.core.domain.utils.ThemeChangeUtil
import com.zeni.settings.domain.utils.Languages

@Singleton
class SharedPrefsManager @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val preferences: SharedPreferences
) {
    val languageChangeUtil by lazy {
        LanguageChangeUtil()
    }
    val themeChangeUtil by lazy {
        ThemeChangeUtil()
    }

    var language: Languages?
        get() = preferences.getString(LANGUAGE, Languages.SPANISH.name)?.let { Languages.valueOf(it) }
        set(value) {
            preferences.edit { putString(LANGUAGE, value?.name) }
            languageChangeUtil.changeLanguage(appContext, value?.code ?: Languages.SPANISH.code)
        }

    var autoDarkTheme: Boolean
        get() = preferences.getBoolean(PrefsKeys.AUTO_DARK_THEME, true)
        set(value) = preferences.edit { putBoolean(PrefsKeys.AUTO_DARK_THEME, value) }

    var darkTheme: Boolean
        get() = preferences.getBoolean(DARK_THEME, themeChangeUtil.getDarkMode())
        set(value) = preferences.edit { putBoolean(DARK_THEME, value) }

    fun setDarkTheme(context: Context, value: Boolean) {
        themeChangeUtil.changeDarkMode(context, value)
        darkTheme = value
    }

    private object PrefsKeys {
        const val LANGUAGE = "language"
        const val AUTO_DARK_THEME = "manual_dark_theme"
        const val DARK_THEME = "dark_theme"
    }
}
