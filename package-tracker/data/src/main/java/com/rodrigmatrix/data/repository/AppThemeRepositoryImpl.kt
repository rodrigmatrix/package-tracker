package com.rodrigmatrix.data.repository

import com.rodrigmatrix.core.resource.ResourceProvider
import com.rodrigmatrix.data.R
import com.rodrigmatrix.data.local.SharedPrefDataSource
import com.rodrigmatrix.domain.entity.SingleChoicePreference
import com.rodrigmatrix.domain.repository.AppThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val THEME_PREF = "app_theme_pref"
private const val SYSTEM_DEFAULT = "system_default"
const val DARK_THEME = "dark_theme"
const val LIGHT_THEME = "light_theme"

class AppThemeRepositoryImpl(
    private val sharedPrefDataSource: SharedPrefDataSource,
    private val resourceProvider: ResourceProvider
): AppThemeRepository {

    override fun getThemeOptions(): Flow<List<SingleChoicePreference>> {
        val selected = getSelectedThemeKey()

        val themeOptionsList = listOf(
            SingleChoicePreference(
                resourceProvider.getString(R.string.system_default),
                SYSTEM_DEFAULT,
                selected == SYSTEM_DEFAULT
            ),
            SingleChoicePreference(
                resourceProvider.getString(R.string.light_theme),
                LIGHT_THEME,
                selected == LIGHT_THEME
            ),
            SingleChoicePreference(
                resourceProvider.getString(R.string.dark_theme),
                DARK_THEME,
                selected == DARK_THEME
            )
        )

        return flow {
            emit(themeOptionsList)
        }
    }

    override fun setTheme(theme: String): Flow<Unit> {
        return flow {
            sharedPrefDataSource.setString(THEME_PREF, theme)
            emit(Unit)
        }
    }

    override fun getSelectedThemeKey(): String {
        return sharedPrefDataSource.getString(
            key = THEME_PREF,
            default = SYSTEM_DEFAULT
        )
    }
}