package com.rodrigmatrix.packagetracker.presentation.packages.viewmodel

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageTrackerAnalytics
import com.rodrigmatrix.domain.repository.SettingsRepository
import com.rodrigmatrix.domain.usecase.DeletePackageUseCase
import com.rodrigmatrix.domain.usecase.FetchAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetPackageProgressStatusUseCase
import com.rodrigmatrix.packagetracker.analytics.ADD_PACKAGE_FAB_CLICK
import com.rodrigmatrix.packagetracker.analytics.ADD_PACKAGE_REQUEST_ERROR
import com.rodrigmatrix.packagetracker.analytics.LOAD_PACKAGES_ERROR
import com.rodrigmatrix.packagetracker.analytics.PACKAGE_DELETE_CLICK
import com.rodrigmatrix.packagetracker.analytics.PACKAGE_DETAILS_CLICK
import com.rodrigmatrix.packagetracker.presentation.packages.model.PackagesFilter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

class PackagesViewModel(
    private val getAllPackagesUseCase: GetAllPackagesUseCase,
    private val fetchAllPackagesUseCase: FetchAllPackagesUseCase,
    private val deletePackageUseCase: DeletePackageUseCase,
    private val settingsRepository: SettingsRepository,
    private val packageTrackerAnalytics: PackageTrackerAnalytics,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<PackagesViewState, PackagesViewEffect>(PackagesViewState()) {

    init {
        loadPackages()
        fetchPackages()
    }

    private fun loadPackages(setLoadingState: Boolean = true) {
        viewModelScope.launch {
            getAllPackagesUseCase()
                .flowOn(coroutineDispatcher)
                .onStart { if (setLoadingState) setState { it.loadingState() } }
                .catch { exception ->
                    packageTrackerAnalytics.sendEvent(
                        LOAD_PACKAGES_ERROR,
                        mapOf(
                            "error" to exception.message.toString()
                        )
                    )
                    setState { it.errorState(exception) }
                }
                .collect { packagesList ->
                    val filteredList = packagesList.filterPackages()
                    setState {
                        it.successState(
                            hasPackages = packagesList.isNotEmpty(),
                            packagesList = filteredList,
                        )
                    }
                }
        }
    }

    private fun List<UserPackage>.filterPackages(): List<UserPackage> {
        return when (viewState.value.packagesListFilter) {
            PackagesFilter.ALL -> this.sortedBy { it.status.delivered }
            PackagesFilter.IN_PROGRESS -> this.filter { it.status.delivered.not() }
            PackagesFilter.DELIVERED -> this.filter { it.status.delivered }
        }
    }

    fun fetchPackages() {
        viewModelScope.launch {
            fetchAllPackagesUseCase()
                .flowOn(coroutineDispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { exception -> setState { it.errorState(exception) } }
                .collect()
        }
    }

    fun deletePackage(packageId: String) {
        viewModelScope.launch {
            deletePackageUseCase(packageId)
                .flowOn(coroutineDispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { exception -> setState { it.errorState(exception) } }
                .collect()
            packageTrackerAnalytics.sendEvent(PACKAGE_DELETE_CLICK)
        }
    }

    fun showDeletePackageDialog(packageId: String) {
        setState { it.showDialogDeleteState(packageId) }
    }

    fun hideDeleteDialog() {
        setState { it.hideDialogDelete() }
    }

    fun onFilterChanged(newFilter: PackagesFilter) {
        setState { it.copy(packagesListFilter = newFilter) }
        loadPackages(setLoadingState = false)
    }

    fun trackAddPackageClick() {
        packageTrackerAnalytics.sendEvent(ADD_PACKAGE_FAB_CLICK)
    }

    fun trackPackageDetailsClick() {
        packageTrackerAnalytics.sendEvent(PACKAGE_DETAILS_CLICK)
    }

    fun onRequestNotificationPermission(isNotificationsEnabled: Boolean) {
        if (isNotificationsEnabled.not()) {
            viewModelScope.launch {
                settingsRepository.getPackageNotificationRequested()
                    .flowOn(coroutineDispatcher)
                    .catch { }
                    .collect { notificationsPermissionRequested ->
                        if (notificationsPermissionRequested.not()) {
                            settingsRepository.setPackageNotificationRequested(true).collect()
                            setEffect { PackagesViewEffect.OnRequestNotificationPermission }
                        }
                    }
            }
        }
    }
}