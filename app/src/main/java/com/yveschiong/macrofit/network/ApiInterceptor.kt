package com.yveschiong.macrofit.network

import com.yveschiong.macrofit.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response



class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Intercept the request and add the api key and format parameters
        val url = request.url().newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .addQueryParameter("format", "json").build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}