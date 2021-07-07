package com.deeb.forcasttask.views.view_models

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.deeb.domain.models.DataState
import com.deeb.domain.usecases.ForecastsUseCase
import com.deeb.forcasttask.bases.BaseViewModel
import com.deeb.forcasttask.ui_models.UiDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ForecastsViewModel @Inject constructor(
    private val forecastsUseCase: ForecastsUseCase
) : BaseViewModel() {
    private val _forecastsLiveData by lazy { MutableLiveData<UiDataState<*>>(UiDataState.Idel) }
    val forecastsLiveData = _forecastsLiveData

    @SuppressLint("CheckResult")
    fun getForecasts(cityName:String){
        forecastsUseCase.excute(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _forecastsLiveData.postValue(UiDataState.Loading)
            }
            .subscribe(
                {
                when(it){
                    is DataState.OnLineData->{
                        _forecastsLiveData.postValue(UiDataState.Success(it.data))
                    }
                    is DataState.OffLineData->{
                        _forecastsLiveData.postValue(UiDataState.Warning(it.data))
                    }
                    is DataState.Error->{
                        _forecastsLiveData.postValue(UiDataState.Error(it.msg))
                    }
                }
                },{
                    _forecastsLiveData.postValue(UiDataState.Error(null))
                }
            ).addTo(compositeDisposable)
    }
}