package com.zeni.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScreenInitial

@Serializable
object ScreenHome

@Serializable
object ScreenTrip

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


@Serializable
data class ScreenExample(
    val id: Int
)