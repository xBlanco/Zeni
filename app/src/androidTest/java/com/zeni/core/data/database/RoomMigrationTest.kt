package com.zeni.core.data.database

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.perfect.you.utils.Constants.DATABASE_NAME
import org.junit.Rule
import org.junit.Test
import kotlin.apply
import kotlin.jvm.java

@SmallTest
class RoomMigrationTest {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        instrumentation = InstrumentationRegistry.getInstrumentation(),
        databaseClass = TripsDatabase::class.java
    )

    @Test
    fun migrateAll() {
        helper.createDatabase(DATABASE_NAME, 1).apply {
            close()
        }

        Room.databaseBuilder(
            context = InstrumentationRegistry.getInstrumentation().targetContext,
            klass = TripsDatabase::class.java,
            name = DATABASE_NAME
        ).addMigrations(*Migrations.migrations).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}