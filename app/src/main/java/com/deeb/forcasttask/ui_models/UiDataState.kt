package com.deeb.forcasttask.ui_models

sealed class UiDataState<T> {
    data class Success<T>(val data:T): UiDataState<T>()
    data class Warning<T>(val data:T): UiDataState<T>()
    object Idel: UiDataState<Nothing>()
    object Loading: UiDataState<Nothing>()
    data class Error(val message:String?): UiDataState<String?>()
}