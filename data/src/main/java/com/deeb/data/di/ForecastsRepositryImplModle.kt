package com.deeb.data.di

import com.deeb.data.local.daos.ForecastsDao
import com.deeb.data.remote.ForecastApi
import com.deeb.data.repositories.DailyWeatherRepoImpl
import com.deeb.domain.repositry.DailyForecastRepositry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
class ForecastsRepositryImplModle {

    @Provides
    @ActivityRetainedScoped
    fun provideForecastsRepositryImplModle(forecastApi: ForecastApi,forecastsDao: ForecastsDao): DailyForecastRepositry {
        return DailyWeatherRepoImpl(forecastApi,forecastsDao)
    }
}
