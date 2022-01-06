package com.rodrigmatrix.di

import android.app.NotificationManager
import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rodrigmatrix.data.BuildConfig
import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.local.PackageLocalDataSourceImpl
import com.rodrigmatrix.data.local.SharedPrefDataSource
import com.rodrigmatrix.data.local.SharedPrefDataSourceImpl
import com.rodrigmatrix.data.local.database.PackagesDatabase
import com.rodrigmatrix.data.remote.FirebaseRemoteConfigDataSource
import com.rodrigmatrix.data.remote.FirebaseRemoteConfigDataSourceImpl
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.data.remote.PackageRemoteDataSourceImpl
import com.rodrigmatrix.data.remote.builder.RetrofitClientGenerator
import com.rodrigmatrix.data.remote.builder.RetrofitServiceGenerator
import com.rodrigmatrix.data.repository.AppThemeRepositoryImpl
import com.rodrigmatrix.data.repository.NotificationsRepositoryImpl
import com.rodrigmatrix.data.repository.PackageRepositoryImpl
import com.rodrigmatrix.data.repository.RemoteConfigRepositoryImpl
import com.rodrigmatrix.data.service.CorreiosService
import com.rodrigmatrix.data.util.NotificationService
import com.rodrigmatrix.data.util.NotificationServiceImpl
import com.rodrigmatrix.domain.repository.AppThemeRepository
import com.rodrigmatrix.domain.repository.NotificationsRepository
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.domain.repository.RemoteConfigRepository
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.DeletePackageUseCase
import com.rodrigmatrix.domain.usecase.EditPackageUseCase
import com.rodrigmatrix.domain.usecase.FetchAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetPackageProgressStatus
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.domain.usecase.SendPackageUpdateNotificationUseCase
import com.rodrigmatrix.domain.usecase.SendPackageUpdatesNotificationsUseCase
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: List<Module>
    get() = useCaseModule + repositoryModule + dataSourceModule + otherModules

private val useCaseModule = module {
    factory { AddPackageUseCase(packageRepository = get()) }
    factory { GetPackageStatusUseCase(packageRepository = get()) }
    factory { GetAllPackagesUseCase(packageRepository = get()) }
    factory { FetchAllPackagesUseCase(packageRepository = get()) }
    factory { DeletePackageUseCase(packageRepository = get()) }
    factory { EditPackageUseCase(packageRepository = get()) }
    factory {
        SendPackageUpdatesNotificationsUseCase(
            getAllPackagesUseCase = get(),
            fetchAllPackagesUseCase = get(),
            getPackageProgressStatus = get(),
            sendNotificationUseCase = get()
        )
    }
    factory { SendPackageUpdateNotificationUseCase(notificationsRepository = get()) }
    factory { GetPackageProgressStatus() }
}

private val repositoryModule = module {
    single<PackageRepository> {
        PackageRepositoryImpl(
            packagesLocalDataSource = get(),
            packagesRemoteDataSource = get()
        )
    }
    factory<NotificationsRepository> { NotificationsRepositoryImpl(notificationService = get()) }
    factory<RemoteConfigRepository> {
        RemoteConfigRepositoryImpl(firebaseRemoteConfigDataSource = get())
    }
    factory<AppThemeRepository> {
        AppThemeRepositoryImpl(
            sharedPrefDataSource = get(),
            resourceProvider = get()
        )
    }
}

private val dataSourceModule = module {
    factory<PackageRemoteDataSource> {
        PackageRemoteDataSourceImpl(
            correiosService = get(),
            resourceProvider = get()
        )
    }
    factory<FirebaseRemoteConfigDataSource> {
        FirebaseRemoteConfigDataSourceImpl(remoteConfig = FirebaseRemoteConfig.getInstance())
    }
    factory<PackageLocalDataSource> { PackageLocalDataSourceImpl(packagesDAO = get()) }
    factory<SharedPrefDataSource> {
        SharedPrefDataSourceImpl(
            sharedPreferences = androidContext()
                .getSharedPreferences(
                    "package_tracker_pref",
                    Context.MODE_PRIVATE
                )
        )
    }
}

@OptIn(ExperimentalSerializationApi::class)
private val otherModules = module {
    factory {
        Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    single { PackagesDatabase(androidApplication()) }
    factory { get<PackagesDatabase>().packagesDAO() }
    single {
        RetrofitClientGenerator().create(
            baseUrl = BuildConfig.CORREIOS_URL,
            converterFactory = get<Json>().asConverterFactory("application/json".toMediaType())
        )
    }
    single { RetrofitServiceGenerator(retrofit = get()) }
    factory { get<RetrofitServiceGenerator>().createService(CorreiosService::class.java) }
    factory {
        androidApplication().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    single<NotificationService> {
        NotificationServiceImpl(
            applicationContext = androidApplication(),
            resourceProvider = get(),
            notificationManager =  get()
        )
    }
}