package com.deeb.domain.usecases

import com.deeb.domain.models.DataState
import com.deeb.domain.models.ForecastModel
import com.deeb.domain.repositry.DailyForecastRepositry
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastsUseCaseTest {
    @Mock
    lateinit var repositry : DailyForecastRepositry
    lateinit var forecastsUseCase:ForecastsUseCase

    @Before
    fun setup(){
         forecastsUseCase=ForecastsUseCase(repositry)
    }
    @Test
    fun `gteForecasts with cityName it return online Data`(){
        val date = "2021-2-12 00:00"
        val tempMin = 21.0
        val tempMax = 21.0
        val weather="rain"
        val cityName = "cairo"
        Mockito.`when`(repositry.getDailyWeather(anyString())).thenReturn(
            Single.just(
                DataState.OnLineData(
                listOf(ForecastModel(date = date,city = cityName,tempMin = tempMin,tempMax = tempMax,weather = weather)))
            )
        )

        val resultObserver = forecastsUseCase.excute(cityName).test()
        resultObserver.assertValue(
            DataState.OnLineData(
                listOf(ForecastModel(date = date,city = cityName,tempMin = tempMin,tempMax = tempMax,weather = weather))
            )
        )
        resultObserver.dispose()
    }

    @Test
    fun `gteForecasts with cityName it return offline Data`(){
        val date = "2021-2-12 00:00"
        val tempMin = 21.0
        val tempMax = 21.0
        val weather="rain"
        val cityName = "cairo"
        Mockito.`when`(repositry.getDailyWeather(anyString())).thenReturn(
            Single.just(
                DataState.OffLineData(
                    listOf(ForecastModel(date = date,city = cityName,tempMin = tempMin,tempMax = tempMax,weather = weather)))
            )
        )
        // act
        val resultObserver = forecastsUseCase.excute(cityName).test()

        // assert
        resultObserver.assertValue(
            DataState.OffLineData(
                listOf(ForecastModel(date = date,city = cityName,tempMin = tempMin,tempMax = tempMax,weather = weather))
            )
        )
        resultObserver.dispose()
    }

    @Test
    fun `gteForecasts with cityName it return error message`(){
       val cityName="not city"
        val message = "city not found"
        Mockito.`when`(repositry.getDailyWeather(anyString())).thenReturn(
            Single.just(
                DataState.Error(message)
            )
        )

        val resultObserver = forecastsUseCase.excute(cityName).test()
        resultObserver.assertValue(DataState.Error(message))

        resultObserver.dispose()
    }
}