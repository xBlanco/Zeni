package com.zeni.core.domain.model

import android.net.Uri

data class TripImage(
    val id: Long,
    val tripId: Long,
    val url: Uri,
    val description: String
)
