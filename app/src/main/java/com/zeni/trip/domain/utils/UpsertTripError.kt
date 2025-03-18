package com.zeni.trip.domain.utils

enum class UpsertTripError {
    NONE,
    DESTINATION_EMPTY,
    START_DATE_EMPTY,
    END_DATE_EMPTY,
    START_AND_END_DATE_EMPTY,
    EMPTY_FIELDS,
    START_DATE_BEFORE_TODAY,
    END_DATE_BEFORE_START_DATE,
}