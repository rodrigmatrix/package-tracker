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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.rodrigmatrix.packagetracker.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.packagetracker.presentation.components.PackageTrackerTopAppBar
import com.rodrigmatrix.packagetracker.presentation.components.Toast
import com.rodrigmatrix.packagetracker.presentation.history.PackageStatus
import com.rodrigmatrix.packagetracker.presentation.history.PackageUpdatesList
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.packageItem
import com.rodrigmatrix.packagetracker.presentation.utils.packageProgressStatus
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailsScreen(
    packageId: String,
    navController: NavController,
    fragmentManager: FragmentManager,
    viewModel: PackagesDetailsViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    viewModel.getPackageStatus(packageId)

    LaunchedEffect("") {
        viewModel.viewEffect.onEach { viewEffect ->
            when (viewEffect) {
                is PackageStatusViewEffect.Close -> {
                    navController.navigateUp()
                }
                is PackageStatusViewEffect.Toast -> {
                    //Toast((viewEffect as PackageStatusViewEffect.Toast).message)
                }
                null -> Unit
            }
        }
    }

    if (viewState.deletePackageDialogVisible) {
        DeletePackageDialog(
            onConfirmButtonClick = { viewModel.deletePackage(packageId) },
            onDismissButtonClick = { viewModel.hideDeleteDialog() }
        )
    }

    if (viewState.editPackageDialogVisible) {

    }

    AnimatedVisibility(
        visible = true
    ) {
        Surface(Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PackageTrackerTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.details),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                AddNewPackageBottomSheetFragment
                                    .newInstance(packageId)
                                    .show(fragmentManager, null)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 16.dp)
                                    .height(24.dp),
                                contentDescription = null
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.showDeleteDialog()
                            }
                        ) {
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
    }
}

@Composable
fun DeletePackageDialog(
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissButtonClick,
        title = {
            Text(text = stringResource(R.string.delete_package_title))
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmButtonClick
            ) {
                Text(text = stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissButtonClick
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
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
                PackageTrackerTopAppBar(
                    title = {
                        Text(text = stringResource(R.string.details))
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

                userPackage?.statusUpdateList?.let {
                    PackageUpdatesList(
                        statusUpdateList = it
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun DetailsScreenPreview() {
    PackageTrackerTheme {
        Details(
            onNavigationItemClick = {},
            packageItem,
            packageProgressStatus
        )
    }
}