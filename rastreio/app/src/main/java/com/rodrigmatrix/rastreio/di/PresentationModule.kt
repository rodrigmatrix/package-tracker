package com.rodrigmatrix.rastreio.di

import com.rodrigmatrix.rastreio.presentation.details.PackagesDetailsViewModel
import com.rodrigmatrix.rastreio.presentation.packages.PackagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { PackagesDetailsViewModel(getPackageStatusUseCase = get()) }
    viewModel { PackagesViewModel(getAllPackagesUseCase = get()) }
}