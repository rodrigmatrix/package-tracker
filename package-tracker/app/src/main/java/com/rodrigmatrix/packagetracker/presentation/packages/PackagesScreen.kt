package com.rodrigmatrix.packagetracker.presentation.packages

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.DeletePackageDialog
import com.rodrigmatrix.packagetracker.presentation.components.SelectableChip
import com.rodrigmatrix.packagetracker.presentation.navigation.NavigationRoutes
import com.rodrigmatrix.packagetracker.presentation.packages.model.PackagesFilter
import com.rodrigmatrix.packagetracker.presentation.packages.viewmodel.PackagesViewEffect
import com.rodrigmatrix.packagetracker.presentation.packages.viewmodel.PackagesViewModel
import com.rodrigmatrix.packagetracker.presentation.packages.viewmodel.PackagesViewState
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.LaunchViewEffect
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewAllTypes
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItemsList
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenContentType
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenNavigationType
import org.koin.androidx.compose.getViewModel

const val PACKAGES_LIST_TAG = "packages_list_tag"

@Composable
fun PackagesScreen(
    navigationType: ScreenNavigationType,
    contentType: ScreenContentType,
    navController: NavController,
    viewModel: PackagesViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    PackagesScreen(
        viewState = viewState,
        navigationType = navigationType,
        onSwipeRefresh = viewModel::fetchPackages,
        onPackageClick = { id ->
            viewModel.trackPackageDetailsClick()
            navController.navigate("package/$id")
        },
        onLongClick = viewModel::showDeletePackageDialog,
        onConfirmDeletePackage = {
            viewModel.deletePackage(viewState.selectedPackageId)
        },
        onDismissDeletePackageDialog = viewModel::hideDeleteDialog,
        onFilterChanged = viewModel::onFilterChanged,
        onAddPackageButtonClicked = {
            navController.navigate(NavigationRoutes.ADD_PACKAGE)
        },
    )

    LaunchViewEffect(viewModel) { effect ->
        when (effect) {
            PackagesViewEffect.OnRequestNotificationPermission -> {
                navController.navigate(NavigationRoutes.NOTIFICATION_DIALOG_ROUTE)
            }
        }
    }

    LaunchedEffect(viewModel) {
        if (Build.VERSION.SDK_INT >= 33) {
            val isNotificationsEnabled = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            viewModel.onRequestNotificationPermission(isNotificationsEnabled)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PackagesScreen(
    viewState: PackagesViewState,
    navigationType: ScreenNavigationType,
    onSwipeRefresh: () -> Unit,
    onPackageClick: (id: String) -> Unit,
    onLongClick: (id: String) -> Unit,
    onConfirmDeletePackage: () -> Unit,
    onDismissDeletePackageDialog: () -> Unit,
    onFilterChanged: (PackagesFilter) -> Unit,
    onAddPackageButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(viewState.isRefreshing, onSwipeRefresh)
    if (viewState.deletePackageDialogVisible) {
        DeletePackageDialog(onConfirmDeletePackage, onDismissDeletePackageDialog)
    }
    val lazyListState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { lazyListState.firstVisibleItemIndex == 0 } }
    Box(modifier.pullRefresh(pullRefreshState)) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                        onFilterChanged = onFilterChanged,
                        state = lazyListState,
                        navigationType = navigationType,
                    )
                }
            }
        }
        if (viewState.hasPackages) {
            PullRefreshIndicator(
                refreshing = viewState.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
        AddPackageFab(
            onAddPackageButtonClicked = onAddPackageButtonClicked,
            expanded = expandedFab,
            modifier = when (navigationType) {
                ScreenNavigationType.BOTTOM_NAVIGATION ->
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 96.dp, end = 16.dp)
                ScreenNavigationType.NAVIGATION_RAIL ->
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PackagesList(
    packagesList: List<UserPackage>,
    navigationType: ScreenNavigationType,
    selectedFilter: PackagesFilter,
    onItemClick: (id: String) -> Unit,
    onLongClick: (id: String) -> Unit,
    onFilterChanged: (PackagesFilter) -> Unit,
    state: LazyListState,
) {
    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = when (navigationType) {
            ScreenNavigationType.BOTTOM_NAVIGATION -> 160.dp
            ScreenNavigationType.NAVIGATION_RAIL -> 16.dp
        }),
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
            composition = composition,
            progress = { progress },
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
            { progress },
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

@Composable
private fun AddPackageFab(
    onAddPackageButtonClicked: () -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        onClick = onAddPackageButtonClicked,
        expanded = expanded,
        icon = {
            Icon(
                imageVector = Icons.Filled.Add,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = null,
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.add),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = modifier,
    )
}

@PreviewAllTypes
@Composable
fun PackagesPreview() {
    PackageTrackerTheme {
        PackagesScreen(
            viewState = PackagesViewState(
                isRefreshing = false,
                packagesList = PreviewPackageItemsList
            ),
            navigationType = ScreenNavigationType.BOTTOM_NAVIGATION,
            onSwipeRefresh = { },
            onPackageClick = { },
            onLongClick = { },
            onConfirmDeletePackage = { },
            onDismissDeletePackageDialog = { },
            onFilterChanged = { },
            onAddPackageButtonClicked = { },
        )
    }
}

@PreviewAllTypes
@Composable
fun PackagesEmptyStatePreview() {
    PackageTrackerTheme {
        PackagesScreen(
            viewState = PackagesViewState(isRefreshing = false),
            navigationType = ScreenNavigationType.BOTTOM_NAVIGATION,
            onSwipeRefresh = { },
            onPackageClick = { },
            onLongClick = { },
            onConfirmDeletePackage = { },
            onDismissDeletePackageDialog = { },
            onFilterChanged = { },
            onAddPackageButtonClicked = { },
        )
    }
}