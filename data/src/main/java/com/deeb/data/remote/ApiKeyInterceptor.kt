package com.deeb.data.remote

import com.deeb.data.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url= originalRequest.url
            .newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .build()
        var newRequest = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}