package com.rodrigmatrix.packagetracker.presentation.settings

import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.SingleChoicePreference

data class SettingsViewState(
    val themeOptionsList: List<SingleChoicePreference<String>> = emptyList(),
    val notificationsIntervalOptionsList: List<SingleChoicePreference<Int>> = emptyList(),
    val selectedTheme: String = "",
    val selectedNotificationInterval: String = "",
    val themeDialogVisible: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val notificationsInterval: Int = 0,
    val notificationIntervalDialogVisible: Boolean = false
): ViewState