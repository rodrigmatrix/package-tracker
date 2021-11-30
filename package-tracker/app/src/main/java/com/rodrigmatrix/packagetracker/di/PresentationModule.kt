package com.rodrigmatrix.packagetracker.di

import com.rodrigmatrix.domain.usecase.GetPackageProgressStatus
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageViewModel
import com.rodrigmatrix.packagetracker.presentation.details.PackagesDetailsViewModel
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
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
}