package com.deeb.data.models.api_response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorReponse(
    val cod: String,
    val message: String
)