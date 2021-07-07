package com.deeb.data.models.api_response

data class ForcastResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<Forcast>?,
    val message: String?
)