package com.rodrigmatrix.packagetracker.presentation.settings

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.entity.SingleChoicePreference
import com.rodrigmatrix.domain.repository.SettingsRepository
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewEffect.ShowToast
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewEffect.UpdateTheme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<SettingsViewState, SettingsViewEffect>(SettingsViewState()) {

    init {
        getThemeOptions()
        getNotificationsPreference()
    }

    private fun getThemeOptions() {
        viewModelScope.launch {
            settingsRepository.getThemeOptions()
                .flowOn(coroutineDispatcher)
                .catch { setEffect { ShowToast(it.message.orEmpty()) } }
                .collect { options ->
                    setState {
                        it.copy(
                            themeOptionsList = options,
                            selectedTheme = options.getSelected(),
                            themeDialogVisible = false
                        )
                    }
                }
        }
    }

    private fun getNotificationsPreference() {
        viewModelScope.launch {
            settingsRepository.getPackageNotificationsPreference()
                .flowOn(coroutineDispatcher)
                .catch { setEffect { ShowToast(it.message.orEmpty()) } }
                .zip(settingsRepository.getPackageNotificationsIntervalList()) { pref, list ->
                    setState {
                        it.copy(
                            notificationsEnabled = pref.enabled,
                            notificationsInterval = pref.minutesUpdateInterval,
                            notificationsIntervalOptionsList = list,
                            selectedNotificationInterval = list.getSelectedInt()
                        )
                    }
                }.collect()
        }
    }

    private fun List<SingleChoicePreference<String>>.getSelected(): String {
        return this.find { it.selected }?.name.orEmpty()
    }

    private fun List<SingleChoicePreference<Int>>.getSelectedInt(): String {
        return this.find { it.selected }?.name.orEmpty()
    }

    fun setTheme(newTheme: String) {
        viewModelScope.launch {
            settingsRepository.setTheme(newTheme)
                .flowOn(coroutineDispatcher)
                .catch { setEffect { ShowToast(it.message.orEmpty()) } }
                .collect {
                    hideThemeDialog()
                    getThemeOptions()
                    setEffect { UpdateTheme }
                }
        }
    }

    fun showThemeDialog() {
        setState {
            it.copy(themeDialogVisible = true)
        }
    }

    fun hideThemeDialog() {
        setState {
            it.copy(themeDialogVisible = false)
        }
    }

    fun showNotificationsDialog() {
        setState {
            it.copy(notificationIntervalDialogVisible = true)
        }
    }

    fun hideNotificationsDialog() {
        setState {
            it.copy(notificationIntervalDialogVisible = false)
        }
    }

    fun onNewNotificationConfig(checked: Boolean, updateInterval: Int) {
        viewModelScope.launch {
            settingsRepository.setPackageNotificationsEnabled(
                checked,
                updateInterval
            )
            .flowOn(coroutineDispatcher)
            .catch { setEffect { ShowToast(it.message.orEmpty()) } }
            .collect {
                getNotificationsPreference()
                hideNotificationsDialog()
            }
        }
    }
}