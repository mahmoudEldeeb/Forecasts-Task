package com.deeb.data.mapers

import com.deeb.data.models.api_response.ForcastResponse
import com.deeb.data.models.entities.ForecastEntity

import com.deeb.domain.models.ForecastModel


fun ForcastResponse.mapToEntity()= this.list?.map {
    ForecastEntity(city = this.city?.name,date = it.dt_txt,
                weather = it.weather?.get(0)?.main,tempMax = it.main.temp_max,tempMin = it.main.temp_min)
    }

fun ForcastResponse.mapToDomain()= this.list?.map {
    ForecastModel(city = this.city?.name, date = it.dt_txt,
            weather = it.weather?.get(0)?.main, tempMax = it.main.temp_max, tempMin = it.main.temp_min)
}

fun List<ForecastEntity>.mapToDomain()= this.map {
        ForecastModel(city = it.city,date = it.date,
                weather = it.weather,tempMax = it.tempMax,tempMin = it.tempMin)
    }
