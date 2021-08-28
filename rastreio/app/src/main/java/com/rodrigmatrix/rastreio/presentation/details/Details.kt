package com.rodrigmatrix.rastreio.presentation.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.rodrigmatrix.rastreio.presentation.history.DisplayUpdatesList
import org.koin.androidx.compose.getViewModel


@Composable
fun Details(packageId: String) {
    val viewModel = getViewModel<PackagesDetailsViewModel>()
    val viewState by viewModel.viewState.collectAsState()

    viewModel.getPackageStatus(packageId)

    Surface(Modifier.fillMaxSize()) {
        DisplayUpdatesList(statusUpdateList = viewState.userPackage?.statusUpdate.orEmpty())
    }
}