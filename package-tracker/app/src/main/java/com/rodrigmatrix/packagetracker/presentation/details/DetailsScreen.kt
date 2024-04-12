package com.rodrigmatrix.packagetracker.presentation.details

import androidx.compose.animation.*
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.PackageTrackerTopAppBar
import com.rodrigmatrix.packagetracker.presentation.history.PackageUpdatesList
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.core.extensions.toast
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.extensions.getCurrentStatusString
import com.rodrigmatrix.packagetracker.extensions.getDaysString
import com.rodrigmatrix.packagetracker.presentation.addpackage.getIconOptions
import com.rodrigmatrix.packagetracker.presentation.components.DeletePackageDialog
import com.rodrigmatrix.packagetracker.presentation.components.PackageImage
import com.rodrigmatrix.packagetracker.presentation.utils.LaunchViewEffect
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewAllTypes
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
                navController.navigate("add_package?packageId=${viewState.userPackage?.packageId}")
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
    Scaffold(
        topBar = {
            PackageTrackerTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditButtonClick) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.edit_package)
                        )
                    }
                    IconButton(onClick = onDeleteButtonClick) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
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
            modifier = Modifier.padding(innerPadding)
        ) {
            if (viewState.userPackage != null) {
                DetailsHeader(userPackage = viewState.userPackage)
                Spacer(modifier = Modifier.height(8.dp))
            }
            PackageUpdatesList(
                statusUpdateList = viewState.userPackage?.statusUpdateList.orEmpty()
            )
        }
    }
}

@Composable
private fun DetailsHeader(
    userPackage: UserPackage,
    modifier: Modifier = Modifier,
) {
    var progress by remember {
        mutableFloatStateOf(-20f)
    }

    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = when {
                userPackage.status.delivered -> 1f
                userPackage.status.outForDelivery -> 0.9f
                userPackage.status.inProgress -> 0.5f
                userPackage.status.mailed -> 0.3f
                else -> 0.1f
            },
            animationSpec = tween(1000),
        ) { value, _ ->
            progress = value
        }
    }
    Column(modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = userPackage.getCurrentStatusString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = userPackage.deliveryType,
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { progress },
            color = MaterialTheme.colorScheme.tertiary,
            strokeCap = StrokeCap.Round,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ) {
            PackageImage(
                imagePath = userPackage.imagePath,
                icon = getIconOptions().find { it.type == userPackage.iconType },
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = userPackage.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = userPackage.packageId,
                    style = MaterialTheme.typography.labelSmall
                )
                userPackage.getDaysString()?.let { daysText ->
                    Text(
                        text = daysText,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
    }
}

@PreviewAllTypes
@Composable
fun DetailsScreenPreview() {
    PackageTrackerTheme {
        DetailsScreen(
            viewState = PackageStatusViewState(userPackage = PreviewPackageItem),
            onBackButtonClick = { },
            onEditButtonClick = { },
            onDeleteButtonClick = { },
            onConfirmDeletePackage = { },
            onDismissDeletePackageDialog = { }
        )
    }
}