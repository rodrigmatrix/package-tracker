package com.rodrigmatrix.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rodrigmatrix.domain.usecase.SendPackageUpdatesNotificationsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdatePackagesAndSendNotificationsWorker(
    appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams), KoinComponent {

    private val sendPackageUpdatesNotificationsUseCase by inject<SendPackageUpdatesNotificationsUseCase>()

    override suspend fun doWork(): Result {
        return try {
            sendPackageUpdatesNotificationsUseCase()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}