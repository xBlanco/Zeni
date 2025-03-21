package com.zeni.itinerary.domain.use_cases.utils

enum class UpsertItineraryError {
    NONE,
    TITLE_EMPTY,
    DESCRIPTION_EMPTY,
    DATE_TIME_EMPTY,
    EMPTY_FIELDS,
    DATE_TIME_BEFORE_TODAY,
    DATE_TIME_NOT_IN_TRIP_PERIOD
}