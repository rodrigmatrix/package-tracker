package com.rodrigmatrix.rastreio.presentation.details

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.rastreio.presentation.components.RastreioTopAppBar
import com.rodrigmatrix.rastreio.presentation.history.PackageStatus
import com.rodrigmatrix.rastreio.presentation.history.PackageUpdatesList
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailsScreen(
    packageId: String,
    navController: NavController,
    viewModel: PackagesDetailsViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    viewModel.getPackageStatus(packageId)

    Details(
        onNavigationItemClick = { navController.navigateUp() },
        userPackage = viewState.userPackage,
        packageProgressStatus = viewState.packageProgressStatus
    )
}

@Composable
fun Details(
    onNavigationItemClick: () -> Unit,
    userPackage: UserPackage?,
    packageProgressStatus: PackageProgressStatus?
) {
    AnimatedVisibility(
        visible = true,
        enter = expandIn(),
        exit = shrinkOut()
    ) {
        Surface(Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RastreioTopAppBar(
                    title = {
                        Text(text = "Detalhes")
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigationItemClick) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )

                packageProgressStatus?.let {
                    PackageStatus(it)
                }

                PackageUpdatesList(
                    statusUpdateList = userPackage?.statusUpdateList.orEmpty()
                )
            }
        }
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun DetailsScreenPreview() {
    val packageItem = UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 4",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
                date = "20/07/2022",
                description = "Saiu para entrega",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "20/07/2022",
                description = "Saiu para entrega",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "20/07/2022",
                description = "Saiu para entrega",
                from = StatusAddress()
            )
        )
    )

    val packageProgressStatus = PackageProgressStatus(
        mailed = true,
        inProgress = true,
        delivered = false
    )

    RastreioTheme {
        Details(
            onNavigationItemClick = {},
            packageItem,
            packageProgressStatus
        )
    }
}