package com.rodrigmatrix.rastreio.presentation.packages

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.rastreio.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun PackagesScreen(
    navController: NavController,
    viewModel: PackagesViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Column {
            SwipeRefresh(
                state = viewState.isRefreshing,
                onRefresh = { viewModel.fetchPackages() }
            ) {
                BoxWithConstraints(modifier = Modifier.weight(1f)) {
                    PackagesList(
                        { id ->
                            navController.navigate("package/$id")
                        },
                        viewState.packagesList
                    )

                    LargeFloatingActionButton(
                        onClick = {
//                        AddNewPackageBottomSheetFragment()
//                            .show(LocalLifecycleOwner.current)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 100.dp),
                        shape = RoundedCornerShape(100)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            modifier = Modifier.size(24.dp),
                            contentDescription = "Add"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PackagesList(
    onItemClick: (id: String) -> Unit,
    packagesList: List<UserPackage>
) {
    Box(Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = packagesList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(packagesList) { packageItem ->
                    Package(onItemClick, packageItem)
                }
            }
        }
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackagesPreview() {
    val items = listOf(
        UserPackage(
            "H6XAJ123BN12",
            "Google Pixel 4",
            "",
            "20/07/2022",
            listOf(
                StatusUpdate(
                    date = "20/07/2022",
                    description = "Saiu para entrega",
                    from = StatusAddress()
                )
            )
        ),
        UserPackage(
            "H6XAJ123BN12",
            "Google Pixel 6 Pro",
            "",
            "20/07/2022",
            listOf(
                StatusUpdate(
                    date = "21/07/2022",
                    description = "Entregue",
                    from = StatusAddress()
                )
            )
        )
    )

    RastreioTheme {
        PackagesList(
            onItemClick = {},
            packagesList = items
        )
    }
}