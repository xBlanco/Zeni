package com.zeni.core.domain.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object ZonedDateTimeUtils {

    fun toUTCMillis(dateTime: ZonedDateTime): Long =
        dateTime.toInstant().toEpochMilli()

    fun fromUTCMillis(millis: Long): ZonedDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
}