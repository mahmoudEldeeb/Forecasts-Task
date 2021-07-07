package com.deeb.data.exceptions

import com.deeb.data.models.api_response.ErrorReponse
import com.squareup.moshi.Moshi
import retrofit2.HttpException

 fun handelHttpError(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder()
                .build()
                .adapter(ErrorReponse::class.java)
            it.toString()
            moshiAdapter.fromJson(it)?.message
        }
    } catch (exception: Exception) {
        null
    }
}
