package com.zeni.itinerary.domain.use_cases.use_cases

import com.zeni.core.domain.model.ItineraryItem
import com.zeni.core.domain.repository.ItineraryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteItineraryUseCase @Inject constructor(
    private val itineraryRepository: ItineraryRepository
) {
    suspend operator fun invoke(itineraryItem: ItineraryItem) {
        itineraryRepository.deleteItineraryItem(itineraryItem)
    }
}