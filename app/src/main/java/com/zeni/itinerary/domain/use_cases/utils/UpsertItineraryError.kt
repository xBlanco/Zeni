package com.zeni.itinerary.domain.use_cases.utils

enum class UpsertItineraryError {
    NONE,
    TITLE_EMPTY,
    DATE_TIME_EMPTY,
    EMPTY_FIELDS,
    DATE_TIME_BEFORE_NOW,
}