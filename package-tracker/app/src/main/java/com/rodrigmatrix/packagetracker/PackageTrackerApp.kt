package com.rodrigmatrix.packagetracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.rodrigmatrix.data.R
import com.rodrigmatrix.data.worker.UpdatePackagesAndSendNotificationsWorker
import com.rodrigmatrix.di.dataModule
import com.rodrigmatrix.packagetracker.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class PackageTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PackageTrackerApp)
            loadKoinModules(listOf(dataModule, presentationModule))
        }
        createNotificationChannel()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            UpdatePackagesAndSendNotificationsWorker::class.java,
            1,
            TimeUnit.HOURS
        ).build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "NOTIFICATION_WORKER",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.updates_channel_name)
            val descriptionText = getString(R.string.updates_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notification_channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}