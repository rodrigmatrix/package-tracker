package com.rodrigmatrix.packagetracker.di

import com.rodrigmatrix.domain.usecase.GetPackageProgressStatus
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageViewModel
import com.rodrigmatrix.packagetracker.presentation.details.PackagesDetailsViewModel
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesViewModel
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewModel
import com.rodrigmatrix.packagetracker.presentation.utils.ThemeUtils
import com.rodrigmatrix.packagetracker.presentation.utils.ThemeUtilsImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule: List<Module>
    get() = viewModelModules + otherModules

private val viewModelModules = module {
    viewModel {
        PackagesDetailsViewModel(
            getPackageStatusUseCase = get(),
            getPackageProgressStatus = get(),
            deletePackageUseCase = get()
        )
    }
    viewModel {
        PackagesViewModel(
            getAllPackagesUseCase = get(),
            fetchAllPackagesUseCase = get(),
            deletePackageUseCase = get(),
            getPackageProgressStatus = GetPackageProgressStatus()
        )
    }
    viewModel { (packageId: String) ->
        AddNewPackageViewModel(
            packageId = packageId,
            addPackageUseCase = get(),
            getPackageStatusUseCase = get(),
            editPackageUseCase = get()
        )
    }
    viewModel {
        SettingsViewModel(
            appThemeRepository = get()
        )
    }
}

private val otherModules = module {
    factory<ThemeUtils> { ThemeUtilsImpl(appThemeRepository = get()) }
}