package com.rodrigmatrix.packagetracker

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.rodrigmatrix.core.di.coreModule
import com.rodrigmatrix.data.util.NotificationService
import com.rodrigmatrix.data.worker.UpdatePackagesAndSendNotificationsWorker
import com.rodrigmatrix.di.dataModule
import com.rodrigmatrix.packagetracker.di.presentationModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class PackageTrackerApp: Application() {

    private val notificationService by inject<NotificationService>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PackageTrackerApp)
            loadKoinModules(listOf(dataModule, presentationModule, coreModule))
        }
        notificationService.createAllNotificationChannel()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            UpdatePackagesAndSendNotificationsWorker::class.java,
            2,
            TimeUnit.MINUTES
        ).build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "NOTIFICATION_WORKER",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }
}