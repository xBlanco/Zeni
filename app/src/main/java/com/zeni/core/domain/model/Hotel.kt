package com.zeni.core.domain.model

import androidx.room.Room

data class Hotel(
    val id: String,
    val name: String,
    val address: String,
    val rating: Int,
    val rooms1: List<Room>,
    val rooms: List<R>,
    val image_url: String
)