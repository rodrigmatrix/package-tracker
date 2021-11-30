package com.rodrigmatrix.packagetracker.presentation.packages

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
import androidx.compose.runtime.Composition
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.packageItemsList
import org.koin.androidx.compose.getViewModel

@Composable
fun PackagesScreen(
    navController: NavController,
    onAddPackageClick: () -> Unit,
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
                    if (viewState.packagesList.isNotEmpty()) {
                        PackagesList(
                            { id ->
                                navController.navigate("package/$id")
                            },
                            viewState.packagesList
                        )
                    } else {
                        PackagesListEmptyState()
                    }

                    LargeFloatingActionButton(
                        onClick = onAddPackageClick,
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
            LazyColumn(
                contentPadding = PaddingValues(bottom = 200.dp)
            ) {
                items(packagesList) { packageItem ->
                    Package(onItemClick, packageItem)
                }
            }
        }
    }
}

@Composable
fun PackagesListEmptyState() {
    Box(Modifier.fillMaxSize()) {
        Text(text = "Nenhuma encomenda cadastrada. Adicione uma encomenda no botão abaixo")
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackagesPreview() {
    PackageTrackerTheme {
        PackagesList(
            onItemClick = {},
            packagesList = packageItemsList
        )
    }
}