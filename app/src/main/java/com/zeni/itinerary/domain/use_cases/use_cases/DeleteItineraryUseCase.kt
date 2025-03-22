package com.zeni.itinerary.domain.use_cases.use_cases

import com.zeni.core.data.repository.ItineraryRepositoryImpl
import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.repository.ItineraryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteItineraryUseCase @Inject constructor(
    private val itineraryRepository: ItineraryRepositoryImpl
) {
    suspend operator fun invoke(tripId: Int, activityId: Int) {
        itineraryRepository.deleteActivity(itineraryRepository.getActivity(tripId, activityId).first())
    }
}