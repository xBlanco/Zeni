package com.zeni.core.domain.model

import java.time.ZonedDateTime

data class ItineraryItem(
    val id: Int,
    val tripId: Int,
    val title: String,
    val description: String,
    val dateTime: ZonedDateTime
)
