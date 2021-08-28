package com.rodrigmatrix.rastreio.di

import com.rodrigmatrix.rastreio.presentation.details.PackagesDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { PackagesDetailsViewModel(get()) }
}