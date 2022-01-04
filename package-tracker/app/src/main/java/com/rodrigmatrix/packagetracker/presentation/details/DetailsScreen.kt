package com.rodrigmatrix.packagetracker.presentation.details

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.rodrigmatrix.packagetracker.presentation.components.DeletePackageDialog
import com.rodrigmatrix.packagetracker.presentation.components.Toast
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailsScreen(
    packageId: String,
    navController: NavController,
    fragmentManager: FragmentManager,
    viewModel: PackagesDetailsViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val viewEffect by viewModel.viewEffect.collectAsState(null)

    viewModel.getPackageStatus(packageId)

    when (viewEffect) {
        is PackageStatusViewEffect.Close -> {
            navController.navigateUp()
        }
        is PackageStatusViewEffect.Toast -> {
            Toast((viewEffect as PackageStatusViewEffect.Toast).message)
        }
        null -> Unit
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .height(24.dp),
                        contentDescription = null
                    )
                }

                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .height(24.dp),
                        contentDescription = null
                    )
                }
            }
        )

        viewState.packageProgressStatus?.let {
            PackageStatus(it)
        }

        PackageUpdatesList(
            statusUpdateList = viewState.userPackage?.statusUpdateList.orEmpty()
        )
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