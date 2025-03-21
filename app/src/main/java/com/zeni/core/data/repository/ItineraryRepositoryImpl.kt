package com.zeni.core.data.repository

import com.zeni.core.domain.model.ItineraryItem
import com.zeni.core.domain.repository.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItineraryRepositoryImpl @Inject constructor(): ItineraryRepository {
    /**
     * List of itinerary items,
     */
    private val itineraryItems = MutableStateFlow(emptyList<ItineraryItem>())

    override fun getItineraryItems(tripId: Int): Flow<List<ItineraryItem>> {
        return itineraryItems.map { items -> items.filter { it.tripId == tripId } }
    }

    override suspend fun addItineraryItem(itineraryItem: ItineraryItem): Int {
        if (itineraryItem.id == -1) {
            itineraryItems.emit(itineraryItems.value + itineraryItem.copy(id = itineraryItems.value.lastOrNull()?.id?.plus(1) ?: 0))
        } else if (itineraryItem.id !in itineraryItems.value.map { it.id }) {
            itineraryItems.emit(itineraryItems.value + itineraryItem)
        } else {
            itineraryItems.emit(itineraryItems.value.map { if (it.id == itineraryItem.id) itineraryItem else it })
        }

        return itineraryItems.value.last().id
    }

    override suspend fun deleteItineraryItem(itineraryItem: ItineraryItem) {
        itineraryItems.emit(itineraryItems.value - itineraryItem)
    }
}