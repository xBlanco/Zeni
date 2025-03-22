package com.zeni.core.domain.model

import java.time.ZonedDateTime

data class Trip(
    val id: Long,
    val destination: String,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
    val coverImage: TripImage? = null,
)