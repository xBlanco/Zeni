package com.zeni.itinerary.domain.use_cases.use_cases

import com.zeni.core.data.repository.ItineraryRepositoryImpl
import com.zeni.core.domain.model.Activity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateItineraryUseCase @Inject constructor(
    private val itineraryRepository: ItineraryRepositoryImpl
) {
    suspend operator fun invoke(itineraryItem: Activity): Int {
        return itineraryRepository.addActivity(itineraryItem)
    }
}