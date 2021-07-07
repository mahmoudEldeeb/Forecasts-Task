package com.deeb.domain.usecases

import com.deeb.domain.repositry.DailyForecastRepositry
import javax.inject.Inject

class  ForecastsUseCase @Inject constructor(private val forecastesRepositry: DailyForecastRepositry) {
    fun excute(cityName: String) =
        forecastesRepositry.getDailyWeather(cityName)
}