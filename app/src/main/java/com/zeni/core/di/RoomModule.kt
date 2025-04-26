package com.zeni.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.zeni.core.data.database.Migrations
import com.zeni.core.data.database.TripsDatabase
import com.zeni.auth.presentation.register.data.UserDao
import com.zeni.core.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val TRIPS_DATABASE_NAME = "trips_database"
    private const val APP_DATABASE_NAME = "app_database"

    @Provides
    @Singleton
    fun provideTripsDatabase(@ApplicationContext appContext: Context): TripsDatabase =
        Room.databaseBuilder(appContext, TripsDatabase::class.java, TRIPS_DATABASE_NAME)
            .addMigrations(*Migrations.migrations)
            .build()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, APP_DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideTripDao(db: TripsDatabase) = db.tripDao()

    @Provides
    @Singleton
    fun provideItineraryDao(db: TripsDatabase) = db.itineraryDao()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
}