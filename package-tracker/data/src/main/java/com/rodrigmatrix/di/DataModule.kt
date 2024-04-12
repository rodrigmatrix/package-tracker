package com.rodrigmatrix.di

import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rodrigmatrix.data.BuildConfig
import com.rodrigmatrix.data.analytics.PackageTrackerAnalyticsImpl
import com.rodrigmatrix.data.interceptors.AccessTokenInterceptor
import com.rodrigmatrix.data.local.AccessTokenDataSource
import com.rodrigmatrix.data.local.AccessTokenDataSourceImpl
import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.local.PackageLocalDataSourceImpl
import com.rodrigmatrix.data.local.SharedPrefDataSource
import com.rodrigmatrix.data.local.SharedPrefDataSourceImpl
import com.rodrigmatrix.data.local.database.PackagesDatabase
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.data.remote.FirebaseRemoteConfigDataSource
import com.rodrigmatrix.data.remote.FirebaseRemoteConfigDataSourceImpl
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.data.remote.PackageRemoteDataSourceImpl
import com.rodrigmatrix.data.remote.builder.RetrofitClientGenerator
import com.rodrigmatrix.data.repository.AccessTokenRepositoryImpl
import com.rodrigmatrix.data.repository.ImageRepositoryImpl
import com.rodrigmatrix.data.repository.SettingsRepositoryImpl
import com.rodrigmatrix.data.repository.NotificationsRepositoryImpl
import com.rodrigmatrix.data.repository.PackageRepositoryImpl
import com.rodrigmatrix.data.repository.RemoteConfigRepositoryImpl
import com.rodrigmatrix.data.service.CorreiosService
import com.rodrigmatrix.data.service.RequestTokenService
import com.rodrigmatrix.data.util.NotificationService
import com.rodrigmatrix.data.util.NotificationServiceImpl
import com.rodrigmatrix.domain.repository.AccessTokenRepository
import com.rodrigmatrix.domain.repository.ImageRepository
import com.rodrigmatrix.domain.repository.SettingsRepository
import com.rodrigmatrix.domain.repository.NotificationsRepository
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.domain.repository.PackageTrackerAnalytics
import com.rodrigmatrix.domain.repository.RemoteConfigRepository
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.DeletePackageUseCase
import com.rodrigmatrix.domain.usecase.EditPackageUseCase
import com.rodrigmatrix.domain.usecase.FetchAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetPackageProgressStatusUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.domain.usecase.SendPackageUpdateNotificationUseCase
import com.rodrigmatrix.domain.usecase.SendPackageUpdatesNotificationsUseCase
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val CORREIOS_RETROFIT = "correios_retrofit"
private const val REQUEST_TOKEN_RETROFIT = "request_token_retrofit"

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
            sendNotificationUseCase = get(),
            packageTrackerAnalytics = get(),
        )
    }
    factory { SendPackageUpdateNotificationUseCase(notificationsRepository = get()) }
    factory { GetPackageProgressStatusUseCase() }
}

private val repositoryModule = module {
    single<PackageRepository> {
        PackageRepositoryImpl(
            packagesLocalDataSource = get(),
            packagesRemoteDataSource = get(),
            packageMapper = get(),
        )
    }
    factory<NotificationsRepository> {
        NotificationsRepositoryImpl(
            notificationService = get(),
            packageTrackerAnalytics = get()
        )
    }
    factory<RemoteConfigRepository> {
        RemoteConfigRepositoryImpl(firebaseRemoteConfigDataSource = get())
    }
    factory<SettingsRepository> {
        SettingsRepositoryImpl(
            sharedPrefDataSource = get(),
            resourceProvider = get()
        )
    }
    single<AccessTokenRepository> {
        AccessTokenRepositoryImpl(
            requestTokenService = get(),
            accessTokenDataSource = get(),
            validateTokenUrl = BuildConfig.CORREIOS_VALIDATE_TOKEN_URL,
            requestTokenUrl = BuildConfig.CORREIOS_REQUEST_TOKEN_URL,
        )
    }
    factory<ImageRepository> {
        ImageRepositoryImpl(contextWrapper = ContextWrapper(androidApplication()))
    }
}

private val dataSourceModule = module {
    factory<PackageRemoteDataSource> { PackageRemoteDataSourceImpl(correiosService = get()) }
    factory<FirebaseRemoteConfigDataSource> {
        FirebaseRemoteConfigDataSourceImpl(remoteConfig = FirebaseRemoteConfig.getInstance())
    }
    factory<PackageLocalDataSource> { PackageLocalDataSourceImpl(packagesDAO = get()) }
    single<SharedPrefDataSource> {
        SharedPrefDataSourceImpl(
            sharedPreferences = androidContext()
                .getSharedPreferences(
                    "package_tracker_pref",
                    Context.MODE_PRIVATE
                )
        )
    }
    single<AccessTokenDataSource> {
        AccessTokenDataSourceImpl(
            sharedPreferences = androidContext()
                .getSharedPreferences(
                    "access_token_pref",
                    Context.MODE_PRIVATE
                )
        )
    }
    factory {
        PackageMapper(getPackageProgressStatusUseCase = get())
    }
}

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
    single(named(CORREIOS_RETROFIT)) {
        RetrofitClientGenerator().create(
            baseUrl = BuildConfig.CORREIOS_PROXY_URL,
            converterFactory = get<Json>().asConverterFactory("application/json".toMediaType()),
            interceptor = AccessTokenInterceptor(accessTokenRepository = get()),
        )
    }
    single(named(REQUEST_TOKEN_RETROFIT)) {
        RetrofitClientGenerator().create(
            baseUrl = BuildConfig.CORREIOS_URL,
            converterFactory = get<Json>().asConverterFactory("application/json".toMediaType()),
        )
    }
    factory<CorreiosService> { get<Retrofit>(named(CORREIOS_RETROFIT)).create(CorreiosService::class.java) }
    factory<RequestTokenService> { get<Retrofit>(named(REQUEST_TOKEN_RETROFIT)).create(RequestTokenService::class.java) }
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
    single<PackageTrackerAnalytics> {
        PackageTrackerAnalyticsImpl(
            firebaseAnalytics = FirebaseAnalytics.getInstance(androidApplication())
        )
    }
}