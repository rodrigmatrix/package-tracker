package com.rodrigmatrix.packagetracker.presentation.utils

import com.rodrigmatrix.data.repository.DARK_THEME
import com.rodrigmatrix.data.repository.LIGHT_THEME
import com.rodrigmatrix.domain.repository.SettingsRepository
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode

class ThemeUtilsImpl(
    private val settingsRepository: SettingsRepository
) : ThemeUtils {

    override fun setTheme() {
        when (settingsRepository.getSelectedThemeKey()) {
            DARK_THEME -> setDefaultNightMode(MODE_NIGHT_YES)
            LIGHT_THEME -> setDefaultNightMode(MODE_NIGHT_NO)
            else -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}