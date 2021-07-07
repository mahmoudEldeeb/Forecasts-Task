package com.deeb.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.deeb.data.local.ForecastDatabase
import com.deeb.data.local.daos.ForecastsDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context
    {
        return application.applicationContext
    }
    @Provides
    @Singleton
    fun provideDatabase(context: Context):ForecastDatabase
    {
        return  Room.databaseBuilder(
            context,
            ForecastDatabase::class.java,
            "forecast_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideDao(databaseModule: ForecastDatabase): ForecastsDao
    {
        return databaseModule.forecastsDao()
    }
}