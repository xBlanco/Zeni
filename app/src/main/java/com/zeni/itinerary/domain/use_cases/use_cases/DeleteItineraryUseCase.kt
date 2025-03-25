package com.zeni.itinerary.domain.use_cases.use_cases

import com.zeni.core.data.repository.ItineraryRepositoryImpl
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteItineraryUseCase @Inject constructor(
    private val itineraryRepository: ItineraryRepositoryImpl
) {
    suspend operator fun invoke(tripName: String, activityId: Long) {
        itineraryRepository.deleteActivity(itineraryRepository.getActivity(tripName, activityId).first())
    }
}