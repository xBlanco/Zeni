package com.zeni.core.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val migration1To2 = object : Migration(startVersion = 1, endVersion = 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
        }
    }

    val migrations = arrayOf<Migration>(
        migration1To2
    )
}