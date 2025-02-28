package com.zeni.core.presentation.navigation

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
object ScreenInitial

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
object ScreenProfile

@Serializable
data class ScreenExample(
    val id: Int
)