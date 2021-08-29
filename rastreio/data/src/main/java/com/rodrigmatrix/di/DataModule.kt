package com.rodrigmatrix.di

import com.google.gson.GsonBuilder
import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.local.PackageLocalDataSourceImpl
import com.rodrigmatrix.data.local.database.PackagesDatabase
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.data.remote.PackageRemoteDataSourceImpl
import com.rodrigmatrix.data.repository.PackageRepositoryImpl
import com.rodrigmatrix.data.service.CorreiosService
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory<PackageRemoteDataSource> { PackageRemoteDataSourceImpl(correiosService = get()) }
    factory<PackageRepository> {
        PackageRepositoryImpl(
            packagesLocalDataSource = get(),
            packagesRemoteDataSource = get()
        )
    }
    factory<PackageLocalDataSource> { PackageLocalDataSourceImpl(packagesDAO = get()) }
    factory { AddPackageUseCase(packageRepository = get()) }
    factory { GetPackageStatusUseCase(packageRepository = get()) }
    factory { GetAllPackagesUseCase(packageRepository = get()) }
    single { PackagesDatabase(androidContext()) }
    single { get<PackagesDatabase>().packagesDAO() }
    factory<CorreiosService> {
        Retrofit.Builder()
        .baseUrl("https://correios.contrateumdev.com.br/api/")
        .client( OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(CorreiosService::class.java)
    }
}