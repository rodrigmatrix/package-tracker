package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.PackageNotificationsPreference
import com.rodrigmatrix.domain.entity.SingleChoicePreference
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getThemeOptions(): Flow<List<SingleChoicePreference<String>>>

    fun setTheme(theme: String): Flow<Unit>

    fun getSelectedThemeKey(): String

    fun getPackageNotificationsPreference(): Flow<PackageNotificationsPreference>

    fun setPackageNotificationsEnabled(
        enabled: Boolean,
        minutesUpdateInterval: Int
    ): Flow<Unit>

    fun getPackageNotificationsIntervalList(): Flow<List<SingleChoicePreference<Int>>>
}