package com.rodrigmatrix.packagetracker.presentation.packages

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.DeletePackageDialog
import com.rodrigmatrix.packagetracker.presentation.components.SelectableChip
import com.rodrigmatrix.packagetracker.presentation.packages.model.PackagesFilter
import com.rodrigmatrix.packagetracker.presentation.packages.viewmodel.PackagesViewModel
import com.rodrigmatrix.packagetracker.presentation.packages.viewmodel.PackagesViewState
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItemsList
import org.koin.androidx.compose.getViewModel

const val PACKAGES_LIST_TAG = "packages_list_tag"

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
        onAddPackageClick = {
            viewModel.trackAddPackageClick()
            onAddPackageClick()
        },
        onPackageClick = { id ->
            viewModel.trackPackageDetailsClick()
            navController.navigate("package/$id")
        },
        onLongClick = { id ->
            viewModel.showDeletePackageDialog(id)
        },
        onConfirmDeletePackage = {
            viewModel.deletePackage(viewState.selectedPackageId)
        },
        onDismissDeletePackageDialog = viewModel::hideDeleteDialog,
        onFilterChanged = { newFilter ->
            viewModel.onFilterChanged(newFilter)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackagesScreen(
    viewState: PackagesViewState,
    onSwipeRefresh: () -> Unit,
    onAddPackageClick: () -> Unit,
    onPackageClick: (id: String) -> Unit,
    onLongClick: (id: String) -> Unit,
    onConfirmDeletePackage: () -> Unit,
    onDismissDeletePackageDialog: () -> Unit,
    onFilterChanged: (PackagesFilter) -> Unit
) {
    if (viewState.deletePackageDialogVisible) {
        DeletePackageDialog(onConfirmDeletePackage, onDismissDeletePackageDialog)
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(viewState.isRefreshing),
        onRefresh = onSwipeRefresh,
        swipeEnabled = viewState.packagesList.isNotEmpty()
    ) {
        Scaffold(
            floatingActionButton = {
                LargeFloatingActionButton(
                    onClick = onAddPackageClick,
                    modifier = Modifier.padding(bottom = 80.dp),
                    shape = RoundedCornerShape(100)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(24.dp),
                        contentDescription = null
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            when {
                viewState.packagesList.isEmpty() && !viewState.isRefreshing && viewState.hasPackages.not() -> {
                    PackagesListEmptyState()
                }
                else -> {
                    PackagesList(
                        packagesList = viewState.packagesList,
                        selectedFilter = viewState.packagesListFilter,
                        onItemClick = {
                            onPackageClick(it)
                        },
                        onLongClick = {
                            onLongClick(it)
                        },
                        onFilterChanged = onFilterChanged
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PackagesList(
    packagesList: List<UserPackage>,
    selectedFilter: PackagesFilter,
    onItemClick: (id: String) -> Unit,
    onLongClick: (id: String) -> Unit,
    onFilterChanged: (PackagesFilter) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 200.dp),
        modifier = Modifier
            .fillMaxSize()
            .testTag(PACKAGES_LIST_TAG)
    ) {
        stickyHeader {
            PackagesFilterHeader(
                selectedFilter = selectedFilter,
                onSelectionChanged = onFilterChanged
            )
        }
        if (packagesList.isEmpty()) {
            item {
                NoPackagesForFilter()
            }
        } else {
            items(packagesList) { packageItem ->
                Package(
                    onItemClick,
                    onLongClick,
                    packageItem
                )
            }
        }
    }
}

@Composable
fun NoPackagesForFilter() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(RawRes(R.raw.empty_box))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
        )
        Text(
            text = stringResource(R.string.no_packages_filter),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 100.dp
                )
        )
    }
}

@Composable
fun PackagesFilterHeader(
    selectedFilter: PackagesFilter,
    onSelectionChanged: (PackagesFilter) -> Unit
) {
    Spacer(Modifier.height(10.dp))
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        SelectableChip(
            title = stringResource(R.string.all),
            isSelected = selectedFilter == PackagesFilter.ALL,
            onclick = { onSelectionChanged(PackagesFilter.ALL) }
        )
        Spacer(Modifier.width(10.dp))
        SelectableChip(
            title = stringResource(R.string.in_progress),
            isSelected = selectedFilter == PackagesFilter.IN_PROGRESS,
            onclick = { onSelectionChanged(PackagesFilter.IN_PROGRESS) }
        )
        Spacer(Modifier.width(10.dp))
        SelectableChip(
            title = stringResource(R.string.delivered),
            isSelected = selectedFilter == PackagesFilter.DELIVERED,
            onclick = { onSelectionChanged(PackagesFilter.DELIVERED) }
        )
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun PackagesListEmptyState() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(RawRes(R.raw.empty_box))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
        )
        Text(
            text = stringResource(R.string.empty_packages),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 100.dp
                )
        )
    }
}

@Preview(name = "Light Theme")
@Preview("Tablet View", device = Devices.PIXEL_C)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackagesPreview() {
    PackageTrackerTheme {
        PackagesScreen(
            viewState = PackagesViewState(
                isRefreshing = false,
                packagesList = PreviewPackageItemsList
            ),
            onSwipeRefresh = { },
            onAddPackageClick = { },
            onPackageClick = { },
            onLongClick =  { },
            onConfirmDeletePackage = { },
            onDismissDeletePackageDialog = { },
            onFilterChanged = { }
        )
    }
}

@Preview(name = "Empty Light Theme")
@Preview(name = "Empty Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PackagesEmptyStatePreview() {
    PackageTrackerTheme {
        PackagesScreen(
            viewState = PackagesViewState(isRefreshing = false),
            onSwipeRefresh = { },
            onAddPackageClick = { },
            onPackageClick = { },
            onLongClick =  { },
            onConfirmDeletePackage = { },
            onDismissDeletePackageDialog = { },
            onFilterChanged = { }
        )
    }
}