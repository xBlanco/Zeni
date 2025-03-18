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
    val tripId: Int? = null
)
@Serializable
data class ScreenTrip(
    val tripId: Int
)

@Serializable
object ScreenItinerary

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