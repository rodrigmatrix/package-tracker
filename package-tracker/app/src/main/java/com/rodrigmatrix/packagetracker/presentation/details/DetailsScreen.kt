package com.rodrigmatrix.packagetracker.presentation.details

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.packagetracker.presentation.components.PackageTrackerTopAppBar
import com.rodrigmatrix.packagetracker.presentation.history.PackageStatus
import com.rodrigmatrix.packagetracker.presentation.history.PackageUpdatesList
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItem
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageProgressStatus
import kotlinx.coroutines.flow.onEach
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import com.rodrigmatrix.core.extensions.toast
import com.rodrigmatrix.packagetracker.presentation.components.DeletePackageDialog
import com.rodrigmatrix.packagetracker.presentation.components.Toast
import com.rodrigmatrix.packagetracker.presentation.utils.LaunchViewEffect
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailsScreen(
    packageId: String,
    navController: NavController,
    fragmentManager: FragmentManager,
    viewModel: PackagesDetailsViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    viewModel.getPackageStatus(packageId)

    LaunchViewEffect (viewModel) { viewEffect ->
        when (viewEffect) {
            is PackageStatusViewEffect.Close -> {
                navController.navigateUp()
            }
            is PackageStatusViewEffect.Toast -> {
                context.toast(viewEffect.message)
            }
        }
    }
    AnimatedVisibility(visible = true) {
        DetailsScreen(
            viewState = viewState,
            onBackButtonClick = navController::navigateUp,
            onEditButtonClick = {
                AddNewPackageBottomSheetFragment
                    .newInstance(packageId)
                    .show(fragmentManager, null)
            },
            onDeleteButtonClick = viewModel::showDeleteDialog,
            onConfirmDeletePackage = {
                viewModel.deletePackage(packageId)
            },
            onDismissDeletePackageDialog = viewModel::hideDeleteDialog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DetailsScreen(
    viewState: PackageStatusViewState,
    onBackButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onConfirmDeletePackage: () -> Unit,
    onDismissDeletePackageDialog: () -> Unit
) {
    if (viewState.deletePackageDialogVisible) {
        DeletePackageDialog(onConfirmDeletePackage, onDismissDeletePackageDialog)
    }
    Scaffold(
        topBar = {
            PackageTrackerTopAppBar(
                title = {
                    Text(
                        text = viewState.userPackage?.name ?: stringResource(R.string.details),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditButtonClick) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(R.string.edit_package)
                        )
                    }
                    IconButton(onClick = onDeleteButtonClick) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = stringResource(R.string.delete_package)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            viewState.packageProgressStatus?.let {
                PackageStatus(it)
            }
            PackageUpdatesList(
                statusUpdateList = viewState.userPackage?.statusUpdateList.orEmpty()
            )
        }
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun DetailsScreenPreview() {
    PackageTrackerTheme {
        DetailsScreen(
            viewState = PackageStatusViewState(
                userPackage = PreviewPackageItem,
                packageProgressStatus = PreviewPackageProgressStatus
            ),
            onBackButtonClick = { },
            onEditButtonClick = { },
            onDeleteButtonClick = { },
            onConfirmDeletePackage = { },
            onDismissDeletePackageDialog = { }
        )
    }
}