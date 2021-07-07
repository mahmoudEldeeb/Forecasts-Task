package com.deeb.forcasttask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deeb.domain.usecases.ForecastsUseCase
import com.deeb.forcasttask.ui_models.UiDataState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import com.deeb.domain.models.DataState
import com.deeb.domain.models.ForecastModel
import com.deeb.forcasttask.views.view_models.ForecastsViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
class ForecastsViewModelTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var forecastsUseCase: ForecastsUseCase
    @Mock
    lateinit var forecastsObserver: Observer<UiDataState<*>>

    @Captor
     lateinit var argumentCaptor: ArgumentCaptor<UiDataState<*>>
        lateinit var viewModel:ForecastsViewModel
        lateinit var list:List<ForecastModel>

    @Before
    fun setup(){
        val date = "2021-2-12 00:00"
        val tempMin = 21.0
        val tempMax = 21.0
        val weather="rain"
        val cityName = "cairo"
     list=listOf(ForecastModel(date = date,city = cityName,tempMin = tempMin,tempMax = tempMax,weather = weather))
        viewModel=ForecastsViewModel(forecastsUseCase)
    }

    @Test
    fun `get forecasts first idel , load then sucess`(){
        Mockito.`when`(forecastsUseCase.excute(anyString())).thenReturn(
            Single.just(
                DataState.OnLineData(list)
            )
        )
        viewModel.forecastsLiveData.observeForever(forecastsObserver)
        viewModel.getForecasts("cairo")
        Mockito.verify(forecastsObserver, Mockito.times(3))
            .onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues
        Assert.assertEquals(UiDataState.Idel, values[0])
        Assert.assertEquals(UiDataState.Loading, values[1])
        Assert.assertEquals(UiDataState.Success(list),values[2])
    }
    @Test
    fun `get forecasts first idel , load then warning`(){
        Mockito.`when`(forecastsUseCase.excute(anyString())).thenReturn(
            Single.just(
                DataState.OffLineData(list)
            )
        )
        viewModel.forecastsLiveData.observeForever(forecastsObserver)
        viewModel.getForecasts("cairo")
        Mockito.verify(forecastsObserver, Mockito.times(3))
            .onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues
        assertEquals(UiDataState.Idel, values[0])
        assertEquals(UiDataState.Loading, values[1])
        assertEquals(UiDataState.Warning(list),values[2])
    }

    @Test
    fun `get forecasts first idel , load then error`(){
        var message="city not found"
        Mockito.`when`(forecastsUseCase.excute(anyString())).thenReturn(
            Single.just(
                DataState.Error(message)
            )
        )
        viewModel.forecastsLiveData.observeForever(forecastsObserver)
        viewModel.getForecasts("not city")
        Mockito.verify(forecastsObserver, Mockito.times(3))
            .onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues
        assertEquals(UiDataState.Idel, values[0])
        assertEquals(UiDataState.Loading, values[1])
        assertEquals(UiDataState.Error(message),values[2])
    }
}