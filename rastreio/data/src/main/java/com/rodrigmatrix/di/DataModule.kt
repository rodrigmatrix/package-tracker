package com.rodrigmatrix.di

import com.google.gson.GsonBuilder
import com.rodrigmatrix.data.remote.PackageStatusRemoteDataSource
import com.rodrigmatrix.data.remote.PackageStatusRemoteDataSourceImpl
import com.rodrigmatrix.domain.repository.PackageStatusRepository
import com.rodrigmatrix.data.repository.PackageStatusRepositoryImpl
import com.rodrigmatrix.data.service.CorreiosService
import com.rodrigmatrix.data.mapper.PackageMapper
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory<PackageStatusRemoteDataSource> { PackageStatusRemoteDataSourceImpl(get()) }
    factory<PackageStatusRepository> { PackageStatusRepositoryImpl(get(), PackageMapper()) }
    factory<CorreiosService> {
        Retrofit.Builder()
        .baseUrl("https://correios.contrateumdev.com.br/api/")
        .client( OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(CorreiosService::class.java)
    }
}