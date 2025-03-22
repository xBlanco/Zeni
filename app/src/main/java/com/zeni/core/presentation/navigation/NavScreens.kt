package com.zeni.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenRegister
@Serializable
object ScreenLogin

@Serializable
object ScreenInitial

@Serializable
object ScreenHome

@Serializable
object ScreenTrips
@Serializable
data class ScreenUpsertTrip(
    val tripId: Long? = null
)
@Serializable
data class ScreenTrip(
    val tripId: Long
)

@Serializable
data class ScreenUpsertActivity(
    val tripId: Long,
    val activityId: Long? = null
)
@Serializable
data class ScreenItinerary(
    val itineraryId: Long
)

@Serializable
object ScreenMore

@Serializable
object ScreenProfile

@Serializable
object ScreenSettings

@Serializable
object ScreenTerms

@Serializable
object ScreenAbout