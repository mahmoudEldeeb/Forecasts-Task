package com.deeb.domain.models


sealed class DataState {
    data class OnLineData(val data:List<ForecastModel>):DataState()
    data class OffLineData(val data:List<ForecastModel>):DataState()
    data class Error(val msg:String?):DataState()
}
