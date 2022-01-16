package com.rodrigmatrix.packagetracker.presentation.settings

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class SettingsViewEffect : ViewEffect {

    data class ShowToast(val message: String): SettingsViewEffect()

    object UpdateTheme: SettingsViewEffect()

    object UpdateNotificationsInterval: SettingsViewEffect()
}