package com.zeni.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenHome

@Serializable
object ScreenTrip

@Serializable
object ScreenItinerary

@Serializable
object ScreenSettings

@Serializable
object ScreenAbout

@Serializable
data class ScreenExample(
    val id: Int
)