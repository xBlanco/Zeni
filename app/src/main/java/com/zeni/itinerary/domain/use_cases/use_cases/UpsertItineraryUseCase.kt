package com.zeni.itinerary.domain.use_cases.use_cases

import com.zeni.core.data.repository.ItineraryRepositoryImpl
import com.zeni.core.domain.model.ItineraryItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertItineraryUseCase @Inject constructor(
    private val itineraryRepository: ItineraryRepositoryImpl
) {
    suspend operator fun invoke(itineraryItem: ItineraryItem): Int {
        return itineraryRepository.addItineraryItem(itineraryItem)
    }
}