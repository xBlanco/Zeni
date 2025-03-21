package com.zeni.settings.domain.utils

import com.zeni.R

enum class Languages(val resId: Int, val code: String) {
    SPANISH(R.string.spanish_language, "es"),
    CATALAN(R.string.catalan_language, "cat"),
    ENGLISH(R.string.english_language, "en"),
    FRENCH(R.string.french_language, "fr"),
    PORTUGUESE(R.string.portuguese_language, "pt"),
}