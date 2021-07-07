package com.deeb.data.repositories

import android.util.Log
import com.deeb.data.exceptions.handelHttpError
import com.deeb.data.local.daos.ForecastsDao
import com.deeb.data.mapers.mapToDomain
import com.deeb.data.mapers.mapToEntity
import com.deeb.data.models.api_response.ErrorReponse
import com.deeb.data.remote.ForecastApi
import com.deeb.domain.models.DataState
import com.deeb.domain.repositry.DailyForecastRepositry
import com.squareup.moshi.Moshi
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableFromAction
import kotlinx.coroutines.handleCoroutineException
import retrofit2.HttpException

class DailyWeatherRepoImpl(private val forecastApi: ForecastApi, private val forecastsDao: ForecastsDao):DailyForecastRepositry {
    override fun getDailyWeather(cityName: String): Single<DataState> {
        return forecastApi.getDailyWeather(cityName)
            .flatMap {
                CompletableFromAction {
                    forecastsDao.insertForecastData(it.mapToEntity() ?: emptyList())
                }.andThen(Single.just(DataState.OnLineData(it.mapToDomain()!!) as DataState))
            }
            .onErrorResumeNext { throwble ->
                var result = forecastsDao.getForecastData(cityName)
                result.flatMap {
                    if (it.isNullOrEmpty()) {
                        Single.just(DataState.Error(handelHttpError(throwable = throwble as HttpException)) as DataState)
                    }
                    else {
                        Single.just(DataState.OffLineData(it.mapToDomain()) as DataState)
                    }
                }
            }
    }

}