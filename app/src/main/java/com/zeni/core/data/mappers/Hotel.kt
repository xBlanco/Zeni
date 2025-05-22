package com.zeni.core.data.mappers

import com.zeni.core.data.database.entities.HotelEntity
import com.zeni.core.data.database.entities.RoomEntity
import com.zeni.core.data.database.entities.RoomImageEntity
import com.zeni.core.data.database.relations.HotelRelation
import com.zeni.core.data.database.relations.RoomWithImages
import com.zeni.core.domain.model.Hotel
import com.zeni.core.domain.model.Room

fun Hotel.toEntity() = HotelEntity(
    id = id,
    name = name,
    address = address,
    rating = rating,
    imageUrl = imageUrl // Usa imageUrl, no image_url
)

fun Room.toEntity(hotelId: String) = RoomEntity(
    id = id,
    hotelId = hotelId,
    roomType = roomType,
    price = price
)

fun Room.imagesToEntities(roomId: String) = images.map { imageUrl ->
    RoomImageEntity(roomId = roomId, imageUrl = imageUrl)
}

fun RoomWithImages.toDomain() = Room(
    id = room.id,
    roomType = room.roomType,
    price = room.price,
    images = images.map { it.imageUrl },
    hotelId = room.hotelId // Asume que RoomEntity tiene hotelId
)

fun HotelRelation.toDomain() = Hotel(
    id = hotel.id,
    name = hotel.name,
    address = hotel.address,
    rating = hotel.rating,
    rooms = rooms.map { it.toDomain() },
    imageUrl = hotel.imageUrl // Usa solo imageUrl
)