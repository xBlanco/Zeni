package com.zeni.core.domain.model

import java.time.ZonedDateTime

data class Activity(
    val id: Int = -1,
    val tripId: Int,
    val title: String,
    val description: String,
    val dateTime: ZonedDateTime
)
