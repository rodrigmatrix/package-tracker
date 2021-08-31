package com.rodrigmatrix.rastreio.presentation.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.rodrigmatrix.rastreio.presentation.history.PackageUpdatesList
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    packageId: String,
    viewModel: PackagesDetailsViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    viewModel.getPackageStatus(packageId)

    Surface(Modifier.fillMaxSize()) {
        PackageUpdatesList(statusUpdateList = viewState.userPackage?.statusUpdate.orEmpty())
    }
}


@Composable
@Preview
fun PreviewDetailsScreen() {
    RastreioTheme {
        PackageUpdatesList(statusUpdateList = listOf())
    }
}