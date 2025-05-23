package com.zeni.core.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.zeni.core.data.database.entities.HotelEntity

data class HotelRelation(
    @Embedded val hotel: HotelEntity,
    @Relation(
        entity = RoomWithImages::class,
        parentColumn = "id",
        entityColumn = "hotel_id"
    )
    val rooms: List<RoomWithImages>
)