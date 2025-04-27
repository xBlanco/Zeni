package com.zeni.settings.di

import com.zeni.settings.repository.UserRepository
import com.zeni.settings.repository.UserRepositoryImpl
import com.zeni.auth.presentation.register.data.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}