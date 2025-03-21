package com.zeni.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.zeni.settings.presentation.components.SharedPrefsManager
//import com.zeni.localpreferences.domain.repository.TaskRepository
//import com.zeni.localpreferences.domain.repository.TaskRepositoryImpl
//import com.zeni.localpreferences.ui.viewmodel.FormValidationViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)

        // context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE) //bad implementation

    @Provides
    @Singleton
    fun provideSharedPrefsManager(
        sharedPreferences: SharedPreferences,
        @ApplicationContext context: Context
    ): SharedPrefsManager =
        SharedPrefsManager(sharedPreferences, context)


    @Provides
    @Singleton
    fun provideTaskRepository(): TaskRepository = TaskRepositoryImpl()

    @Provides
    @Singleton
    fun provideFormValidationViewModel(
        @ApplicationContext context: Context
    ): FormValidationViewModel = FormValidationViewModel(context)

}
