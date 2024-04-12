package com.rodrigmatrix.data.remote.builder

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitClientGenerator {

    fun create(
        baseUrl: String,
        converterFactory: Converter.Factory,
        interceptor: Interceptor? = null,
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            interceptor?.let { addInterceptor(it) }
            addInterceptor(HttpLoggingInterceptor().apply { setLevel(Level.BODY) })
        }.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}