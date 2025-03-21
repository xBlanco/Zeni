package com.zeni.core.domain.model

import androidx.compose.runtime.Immutable
import java.time.ZonedDateTime
import java.util.Date

data class Trip(
    val id: Int = -1, // If 0, autogenerate a new id
    val destination: String,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
)