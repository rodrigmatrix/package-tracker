package com.rodrigmatrix.rastreio

import android.app.Application
import com.rodrigmatrix.di.dataModule
import com.rodrigmatrix.rastreio.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class RastreioApp: Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RastreioApp)
            loadKoinModules(listOf(dataModule, presentationModule))
        }
    }
}