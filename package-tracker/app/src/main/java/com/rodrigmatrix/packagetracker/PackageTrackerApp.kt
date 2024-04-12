package com.rodrigmatrix.packagetracker

import android.app.Application
import androidx.work.*
import com.rodrigmatrix.core.di.coreModule
import com.rodrigmatrix.data.util.NotificationService
import com.rodrigmatrix.data.worker.UpdatePackagesAndSendNotificationsWorker
import com.rodrigmatrix.di.dataModule
import com.rodrigmatrix.packagetracker.di.presentationModule
import com.rodrigmatrix.packagetracker.presentation.utils.ThemeUtils
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class PackageTrackerApp: Application() {

    private val notificationService by inject<NotificationService>()
    private val themeUtils by inject<ThemeUtils>()

    override fun onCreate() {
        super.onCreate()
        startKoin()
        notificationService.createAllNotificationChannel()
        startNotificationWorker()
        themeUtils.setTheme()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@PackageTrackerApp)
            loadKoinModules(dataModule + presentationModule + coreModule)
        }
    }

    private fun startNotificationWorker() {
        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            UpdatePackagesAndSendNotificationsWorker::class.java,
            repeatInterval = 1,
            TimeUnit.HOURS,
        ).setConstraints(workerConstraints).build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "NOTIFICATION_WORKER",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }
}