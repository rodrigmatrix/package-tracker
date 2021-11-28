package com.rodrigmatrix.data.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rodrigmatrix.data.R
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.usecase.FetchAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetPackageNotificationsUseCase
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

private const val NOTIFICATION_CHANNEL_ID = "notification_channel"

class UpdatePackagesAndSendNotificationsWorker(
    appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams), KoinComponent {

    private val getAllPackagesUseCase by inject<GetAllPackagesUseCase>()
    private val fetchAllPackagesUseCase by inject<FetchAllPackagesUseCase>()
    private val getPackageNotificationsUseCase = GetPackageNotificationsUseCase()

    override suspend fun doWork(): Result {
        return try {
            val cachedPackages = getAllPackagesUseCase().first()
            val updatedPackages = fetchAllPackagesUseCase().first()

            cachedPackages.forEachIndexed { index, userPackage ->
                getPackageNotificationsUseCase(userPackage, updatedPackages[index]).forEach {
                    sendNotification(userPackage, it)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun sendNotification(userPackage: UserPackage, statusUpdate: StatusUpdate) {
        val description = statusUpdate.description + " - " + statusUpdate.date

        val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(userPackage.name)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(Random.nextInt(), builder)
    }
}