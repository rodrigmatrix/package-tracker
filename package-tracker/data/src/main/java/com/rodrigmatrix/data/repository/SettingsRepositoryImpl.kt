package com.rodrigmatrix.data.repository

import com.rodrigmatrix.core.resource.ResourceProvider
import com.rodrigmatrix.data.R
import com.rodrigmatrix.data.local.SharedPrefDataSource
import com.rodrigmatrix.domain.entity.PackageNotificationsPreference
import com.rodrigmatrix.domain.entity.SingleChoicePreference
import com.rodrigmatrix.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

private const val PACKAGE_NOTIFICATIONS_ENABLED = "package_notifications_enabled"
private const val PACKAGE_NOTIFICATIONS_INTERVAL_MINUTES = "package_notifications_interval_minutes"
private const val THEME_PREF = "app_theme_pref"
private const val SYSTEM_DEFAULT = "system_default"
const val DARK_THEME = "dark_theme"
const val LIGHT_THEME = "light_theme"
private const val FIFTEEN_MINUTES = 15
private const val THIRTY_MINUTES = 30
private const val FORTY_FIVE_MINUTES = 45
private const val SIXTY_MINUTES = 60
private const val NINETY_MINUTES = 90

class SettingsRepositoryImpl(
    private val sharedPrefDataSource: SharedPrefDataSource,
    private val resourceProvider: ResourceProvider
): SettingsRepository {

    override fun getThemeOptions(): Flow<List<SingleChoicePreference<String>>> {
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
            emit(sharedPrefDataSource.setString(THEME_PREF, theme))
        }
    }

    override fun getSelectedThemeKey(): String {
        return sharedPrefDataSource.getString(
            key = THEME_PREF,
            default = SYSTEM_DEFAULT
        )
    }

    override fun getPackageNotificationsPreference(): Flow<PackageNotificationsPreference> {
        return flow {
            val preference = PackageNotificationsPreference(
                enabled = sharedPrefDataSource.getBoolean(
                    PACKAGE_NOTIFICATIONS_ENABLED,
                    true
                ),
                minutesUpdateInterval = sharedPrefDataSource.getInt(
                    PACKAGE_NOTIFICATIONS_INTERVAL_MINUTES,
                    default = 60
                )
            )
            emit(preference)
        }
    }

    override fun setPackageNotificationsEnabled(
        enabled: Boolean,
        minutesUpdateInterval: Int
    ): Flow<Unit> {
        return flow {
            sharedPrefDataSource.setBoolean(PACKAGE_NOTIFICATIONS_ENABLED, enabled)
            sharedPrefDataSource.setInt(
                PACKAGE_NOTIFICATIONS_INTERVAL_MINUTES,
                minutesUpdateInterval
            )
            emit(Unit)
        }
    }

    override fun getPackageNotificationsIntervalList(): Flow<List<SingleChoicePreference<Int>>> {
        return flow {
            val selected = getPackageNotificationsPreference().first().minutesUpdateInterval

            val intervalList = listOf(
                SingleChoicePreference(
                    FIFTEEN_MINUTES.toString(),
                    FIFTEEN_MINUTES,
                    selected == FIFTEEN_MINUTES
                ),
                SingleChoicePreference(
                    THIRTY_MINUTES.toString(),
                    THIRTY_MINUTES,
                    selected == THIRTY_MINUTES
                ),
                SingleChoicePreference(
                    FORTY_FIVE_MINUTES.toString(),
                    FORTY_FIVE_MINUTES,
                    selected == FORTY_FIVE_MINUTES
                ),
                SingleChoicePreference(
                    SIXTY_MINUTES.toString(),
                    SIXTY_MINUTES,
                    selected == SIXTY_MINUTES
                ),
                SingleChoicePreference(
                    NINETY_MINUTES.toString(),
                    NINETY_MINUTES,
                    selected == NINETY_MINUTES
                )
            )
            emit(intervalList)
        }
    }
}