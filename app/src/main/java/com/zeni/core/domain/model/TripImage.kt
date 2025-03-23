package com.zeni.core.domain.model

import android.net.Uri

data class TripImage(
    val id: Long,
    val tripName: String,
    val url: Uri,
    val description: String
)
