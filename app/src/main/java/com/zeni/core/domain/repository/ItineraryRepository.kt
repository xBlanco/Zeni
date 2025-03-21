package com.zeni.core.domain.repository

import com.zeni.core.domain.model.ItineraryItem
import kotlinx.coroutines.flow.Flow

interface ItineraryRepository {
    fun getItineraryItems(tripId: Int): Flow<List<ItineraryItem>>

    suspend fun addItineraryItem(itineraryItem: ItineraryItem): Int

    suspend fun deleteItineraryItem(itineraryItem: ItineraryItem)
}