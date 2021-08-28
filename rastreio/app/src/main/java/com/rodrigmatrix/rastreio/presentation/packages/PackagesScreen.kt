package com.rodrigmatrix.rastreio.presentation.packages

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

@Composable
fun PackagesScreen() {
    val viewModel = getViewModel<PackagesViewModel>()
}