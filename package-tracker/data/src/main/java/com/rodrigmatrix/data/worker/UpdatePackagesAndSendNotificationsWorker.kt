package com.rodrigmatrix.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rodrigmatrix.domain.repository.SettingsRepository
import com.rodrigmatrix.domain.usecase.SendPackageUpdatesNotificationsUseCase
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdatePackagesAndSendNotificationsWorker(
    appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams), KoinComponent {

    private val sendPackageUpdatesNotificationsUseCase by inject<SendPackageUpdatesNotificationsUseCase>()
    private val settingsRepository by inject<SettingsRepository>()

    override suspend fun doWork(): Result {
        return try {
            val notificationPref = settingsRepository.getPackageNotificationsPreference().first()
            if (notificationPref.enabled) {
                sendPackageUpdatesNotificationsUseCase()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}