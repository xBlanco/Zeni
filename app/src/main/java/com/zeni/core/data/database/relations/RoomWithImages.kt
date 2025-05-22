package com.zeni.core.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zeni.core.data.database.entities.RoomEntity
import com.zeni.core.data.database.entities.RoomImageEntity

data class RoomWithImages(
    @Embedded val room: RoomEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "room_id"
    )
    val images: List<RoomImageEntity>
)