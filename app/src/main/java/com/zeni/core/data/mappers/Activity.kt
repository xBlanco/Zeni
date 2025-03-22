package com.zeni.core.data.mappers

import com.zeni.core.data.database.entities.ActivityEntity
import com.zeni.core.domain.model.Activity

fun Activity.toEntity() = ActivityEntity(
    id = id,
    tripId = tripId,
    title = title,
    description = description,
    dateTime = dateTime
)

fun ActivityEntity.toDomain() = Activity(
    id = id,
    tripId = tripId,
    title = title,
    description = description,
    dateTime = dateTime
)