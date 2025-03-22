package com.zeni.core.data.mappers

import androidx.core.net.toUri
import com.zeni.core.data.database.entities.TripImageEntity
import com.zeni.core.domain.model.TripImage

fun TripImage.toEntity() = TripImageEntity(
    id = id,
    tripId = tripId,
    url = url.toString(),
    description = description
)

fun TripImageEntity.toDomain() = TripImage(
    id = id,
    tripId = tripId,
    url = url.toUri(),
    description = description
)