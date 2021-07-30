package com.rodrigmatrix.rastreio.di

import com.rodrigmatrix.rastreio.presentation.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainActivityViewModel(get()) }
}