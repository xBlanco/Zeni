package com.zeni.core.domain.model

data class AvailabilityResponse(
    val hotel_id: String,
    val available_rooms: List<String>,
    val available: Boolean
)