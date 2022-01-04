package com.rodrigmatrix.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG_RESPONSE_BODY = "RESPONSE_BODY"

class HttpLoggingInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.d(TAG_RESPONSE_BODY, response.body.toString())


        return response
    }
}
