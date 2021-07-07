package com.deeb.data.models.api_response

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)