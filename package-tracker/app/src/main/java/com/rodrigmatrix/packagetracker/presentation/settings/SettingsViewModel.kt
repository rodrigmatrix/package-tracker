package com.rodrigmatrix.packagetracker.presentation.settings

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.entity.SingleChoicePreference
import com.rodrigmatrix.domain.repository.AppThemeRepository
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewEffect.ShowToast
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewEffect.UpdateTheme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val appThemeRepository: AppThemeRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<SettingsViewState, SettingsViewEffect>(SettingsViewState()) {

    init {
        getThemeOptions()
    }

    private fun getThemeOptions() {
        viewModelScope.launch {
            appThemeRepository.getThemeOptions()
                .flowOn(coroutineDispatcher)
                .catch { setEffect { ShowToast(it.message.orEmpty()) } }
                .collect { options ->
                    setState {
                        it.copy(
                            singleOptionPreferences = options,
                            selectedTheme = options.getSelected(),
                            themeDialogVisible = false
                        )
                    }
                }
        }
    }

    private fun List<SingleChoicePreference>.getSelected(): String {
        return this.find { it.selected }?.name.orEmpty()
    }

    fun setTheme(newTheme: String) {
        viewModelScope.launch {
            appThemeRepository.setTheme(newTheme)
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
}