package com.zeni.core.util

import android.util.Log

object DatabaseLogger {
    private const val TAG_DB = "ZeniDatabase"

    fun dbOperation(message: String) {
        Log.d(TAG_DB, message)
    }

    fun dbError(message: String, throwable: Throwable? = null) {
        Log.e(TAG_DB, message, throwable)
    }
}