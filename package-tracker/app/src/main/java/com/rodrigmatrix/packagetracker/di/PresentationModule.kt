package com.rodrigmatrix.packagetracker.di

import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageViewModel
import com.rodrigmatrix.packagetracker.presentation.details.PackagesDetailsViewModel
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        PackagesDetailsViewModel(
            getPackageStatusUseCase = get(),
            getPackageProgressStatus = get()
        )
    }
    viewModel {
        PackagesViewModel(
            getAllPackagesUseCase = get(),
            fetchAllPackagesUseCase = get(),
            deletePackageUseCase = get()
        )
    }
    viewModel { AddNewPackageViewModel(addPackageUseCase = get()) }
}