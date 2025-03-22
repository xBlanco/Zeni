package com.zeni.core.domain.model

import java.time.ZonedDateTime

data class Activity(
    val id: Long,
    val tripId: Long,
    val title: String,
    val description: String,
    val dateTime: ZonedDateTime
)
