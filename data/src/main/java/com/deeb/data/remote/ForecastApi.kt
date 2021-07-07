package com.deeb.data.remote

import com.deeb.data.models.api_response.ForcastResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET( "/data/2.5/forecast")
    fun getDailyWeather(
        @Query("q") cityName: String
    ): Single<ForcastResponse>



}