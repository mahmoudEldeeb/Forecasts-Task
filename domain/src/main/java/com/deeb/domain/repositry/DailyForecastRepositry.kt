package com.deeb.domain.repositry

import com.deeb.domain.models.DataState
import io.reactivex.Single

interface DailyForecastRepositry {
    fun getDailyWeather(
        cityName: String,
    ):  Single<DataState>
}