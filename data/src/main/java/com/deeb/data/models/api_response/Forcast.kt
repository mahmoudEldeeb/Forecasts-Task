package com.deeb.data.models.api_response

data class Forcast(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>?,
)