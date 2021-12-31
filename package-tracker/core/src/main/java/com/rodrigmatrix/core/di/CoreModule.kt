package com.rodrigmatrix.core.di

import com.rodrigmatrix.core.resource.ResourceProvider
import com.rodrigmatrix.core.resource.ResourceProviderImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreModule = module {
    single<ResourceProvider> { ResourceProviderImpl(androidApplication()) }
}