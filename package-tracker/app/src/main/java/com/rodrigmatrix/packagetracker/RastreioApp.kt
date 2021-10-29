package com.rodrigmatrix.packagetracker

import android.app.Application
import com.rodrigmatrix.di.dataModule
import com.rodrigmatrix.packagetracker.di.presentationModule
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