package com.zeni.trip.domain.utils

enum class UpsertTripError {
    NONE,
    NAME_EMPTY,
    DESTINATION_EMPTY,
    START_DATE_EMPTY,
    END_DATE_EMPTY,
    START_AND_END_DATE_EMPTY,
    EMPTY_FIELDS,
    NAME_ALREADY_EXISTS,
    START_DATE_BEFORE_TODAY,
    END_DATE_BEFORE_START_DATE,
}