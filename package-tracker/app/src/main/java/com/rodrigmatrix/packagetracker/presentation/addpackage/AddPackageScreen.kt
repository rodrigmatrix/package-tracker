package com.rodrigmatrix.packagetracker.presentation.addpackage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodrigmatrix.core.extensions.toast
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.PackageImage
import com.rodrigmatrix.packagetracker.presentation.components.TextEdit
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.LaunchViewEffect
import com.rodrigmatrix.packagetracker.presentation.utils.MaskVisualTransformation
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewAllTypes
import com.rodrigmatrix.packagetracker.presentation.utils.RegexValidators
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddPackageScreen(
    packageId: String?,
    navController: NavController,
    viewModel: AddNewPackageViewModel = getViewModel {
        parametersOf(packageId)
    },
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::onImageSelected,
    )

    AddPackageScreen(
        viewState = viewState,
        onNameValueChanged = viewModel::onNameChanged,
        onPackageIdValueChanged = viewModel::onPackageIdChanged,
        onAddButtonClicked = {
            if (viewState.isEditPackage) {
                viewModel.editPackage()
            } else {
                viewModel.addPackage()
            }
        },
        onBackButtonClicked = { navController.navigateUp() },
        onChangeDialogState = viewModel::onChangeDialogState,
        onChoseImageOptionSelected = viewModel::onChoseImageOptionSelected,
        onIconOptionSelected = viewModel::onIconSelected,
    )
    LaunchViewEffect(viewModel) { viewEffect ->
        when (viewEffect) {
            AddPackageViewEffect.GoBack -> {
                navController.navigateUp()
            }
            is AddPackageViewEffect.ShowToast -> {
                context.toast(viewEffect.message)
            }
            AddPackageViewEffect.OpenImageSelector -> {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPackageScreen(
    viewState: AddPackageViewState,
    onNameValueChanged: (String) -> Unit,
    onPackageIdValueChanged: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onChangeDialogState: (AddPackageDialogState) -> Unit,
    onChoseImageOptionSelected: (ChooseImageType) -> Unit,
    onIconOptionSelected: (IconOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when (viewState.dialogState) {
        AddPackageDialogState.ChooseImageDialog -> ChooseImageDialog(
            onDismiss = { onChangeDialogState(AddPackageDialogState.Empty) },
            onClick = onChoseImageOptionSelected,
        )
        AddPackageDialogState.ChooseIconDialog -> ChooseIconDialog(
            onDismiss = { onChangeDialogState(AddPackageDialogState.Empty) },
            onIconSelected = onIconOptionSelected,
        )
        AddPackageDialogState.Empty -> Unit
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(
                    onClick = onBackButtonClicked,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        )

        if (viewState.isLoading) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
        }

        Text(
            text = stringResource(viewState.title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    bottom = 16.dp
                )
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            if (viewState.imagePath != null || viewState.icon != null) {
                Box(
                    modifier = Modifier.clickable(
                        onClick = {
                            onChangeDialogState(AddPackageDialogState.ChooseImageDialog)
                        }
                    )
                ) {
                    PackageImage(
                        imagePath = viewState.imagePath,
                        icon = viewState.icon,
                        modifier = Modifier.size(80.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Adicionar Imagem",
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 16.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .size(24.dp)
                            .padding(4.dp)
                            .align(Alignment.TopEnd),
                    )
                }
            } else {
                IconButton(
                    onClick = { onChangeDialogState(AddPackageDialogState.ChooseImageDialog) },
                    modifier = Modifier.size(80.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddPhotoAlternate,
                        contentDescription = "Adicionar Imagem",
                        modifier = Modifier.size(48.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextEdit(
                value = viewState.nameText,
                onValueChange = onNameValueChanged,
                singleLine = true,
                error = viewState.nameError,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                label = {
                    Text(
                        text = stringResource(R.string.name),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            TextEdit(
                value = viewState.packageIdText,
                error = viewState.packageIdError,
                onValueChange = onPackageIdValueChanged,
                keyboardOptions = viewState.keyboardOptions,
                enabled = viewState.isEditPackage.not(),
                singleLine = true,
                label = {
                    Text(
                        text = stringResource(R.string.package_id),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 10.dp
                    )
                    .clickable {
                        if (viewState.isEditPackage) {
                            copyPackageIdToClipboard(context, viewState.packageIdText)
                        }
                    }
            )
            if (viewState.isEditPackage) {
                IconButton(
                    onClick = {
                        copyPackageIdToClipboard(context, viewState.packageIdText)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.copy_package_id)
                    )
                }
            }
        }
        Button(
            onClick = onAddButtonClicked,
            enabled = viewState.isLoading.not(),
            modifier = Modifier
                .height(46.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = viewState.actionButton),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun copyPackageIdToClipboard(context: Context, packageId: String?) {
    val clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText(
        context.getString(R.string.package_id_copied),
        packageId
    )
    clipboard?.setPrimaryClip(clip)
    context.toast(R.string.package_id_copied)
}

@PreviewAllTypes
@Composable
fun AddPackagePreview() {
    PackageTrackerTheme {
        AddPackageScreen(
            viewState = AddPackageViewState(),
            onNameValueChanged = { },
            onPackageIdValueChanged = { },
            onAddButtonClicked = { },
            onBackButtonClicked = { },
            onChangeDialogState = { },
            onChoseImageOptionSelected = { },
            onIconOptionSelected = { },
        )
    }
}

@PreviewAllTypes
@Composable
fun EditPackagePreview() {
    PackageTrackerTheme {
        AddPackageScreen(
            viewState = AddPackageViewState(isEditPackage = true),
            onNameValueChanged = { },
            onPackageIdValueChanged = { },
            onAddButtonClicked = { },
            onBackButtonClicked = { },
            onChangeDialogState = { },
            onChoseImageOptionSelected = { },
            onIconOptionSelected = { },
        )
    }
}