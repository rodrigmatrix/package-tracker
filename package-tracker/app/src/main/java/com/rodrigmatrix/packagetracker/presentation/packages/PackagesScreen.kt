package com.rodrigmatrix.packagetracker.presentation.packages

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItemsList
import org.koin.androidx.compose.getViewModel

@Composable
fun PackagesScreen(
    navController: NavController,
    onAddPackageClick: () -> Unit,
    viewModel: PackagesViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    PackagesScreen(
        viewState = viewState,
        onSwipeRefresh = viewModel::fetchPackages,
        onAddPackageClick = onAddPackageClick,
        onPackageClick = { id ->
            navController.navigate("package/$id")
        }
    )
}

@Composable
private fun PackagesScreen(
    viewState: PackagesViewState,
    onSwipeRefresh: () -> Unit,
    onAddPackageClick: () -> Unit,
    onPackageClick: (id: String) -> Unit
) {
    Column {
        SwipeRefresh(
            state = viewState.isRefreshing,
            onRefresh = onSwipeRefresh
        ) {
            BoxWithConstraints(modifier = Modifier.weight(1f)) {
                when {
                    viewState.packagesList.isNotEmpty() -> {
                        PackagesList(
                            onItemClick = {
                                onPackageClick(it)
                            },
                            viewState.packagesList
                        )
                    }

                    else ->  PackagesListEmptyState()
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

@Composable
private fun PackagesList(
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
private fun PackagesListEmptyState() {
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
        PackagesScreen(
            viewState = PackagesViewState(
                isRefreshing = SwipeRefreshState(false),
                packagesList = PreviewPackageItemsList
            ),
            onSwipeRefresh = { },
            onAddPackageClick = { },
            onPackageClick = { }
        )
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PackagesEmptyStatePreview() {
    PackageTrackerTheme {
        PackagesScreen(
            viewState = PackagesViewState(),
            onSwipeRefresh = { },
            onAddPackageClick = { },
            onPackageClick = { }
        )
    }
}