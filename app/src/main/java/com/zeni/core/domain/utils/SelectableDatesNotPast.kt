package com.zeni.core.domain.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
object SelectableDatesNotPast : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val selectedDate = LocalDate.ofEpochDay(utcTimeMillis / (24 * 60 * 60 * 1000))
        return selectedDate >= LocalDate.now()
    }

    override fun isSelectableYear(year: Int): Boolean {
        val calendar = Calendar.getInstance()
        return year >= calendar.get(Calendar.YEAR)
    }
}