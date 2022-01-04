package com.rodrigmatrix.packagetracker.presentation.settings

import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.SingleChoicePreference

data class SettingsViewState(
    val singleOptionPreferences: List<SingleChoicePreference> = emptyList(),
    val selectedTheme: String = "",
    val themeDialogVisible: Boolean = false
): ViewState