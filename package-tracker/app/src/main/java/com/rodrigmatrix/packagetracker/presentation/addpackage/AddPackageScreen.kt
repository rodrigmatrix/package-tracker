package com.rodrigmatrix.packagetracker.presentation.addpackage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.core.extensions.toast
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.TextEdit
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun AddPackageScreen(
    packageId: String?,
    viewModel: AddNewPackageViewModel = getViewModel {
        parametersOf(packageId)
    }
) {
    val viewState by viewModel.viewState.collectAsState()

    AddPackageScreen(
        viewState = viewState,
        onNameValueChanged = {
            viewModel.onNameChanged(it)
        },
        onPackageIdValueChanged = {
            viewModel.onPackageIdChanged(it)
        },
        onAddButtonClicked = {
            if (viewState.isEditPackage) {
                viewModel.editPackage()
            } else {
                viewModel.addPackage()
            }
        }
    )
}

@Composable
fun AddPackageScreen(
    viewState: AddPackageViewState,
    onNameValueChanged: (String) -> Unit,
    onPackageIdValueChanged: (String) -> Unit,
    onAddButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    Column {
        Text(
            text = if (viewState.isEditPackage) {
                stringResource(R.string.edit_package)
            } else {
                stringResource(R.string.add_package)
            },
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    bottom = 16.dp
                )
        )

        TextEdit(
            value = viewState.nameText,
            onValueChange = onNameValueChanged,
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.name),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 10.dp
                )
        )

        TextEdit(
            value = viewState.packageIdText,
            onValueChange = onPackageIdValueChanged,
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
                ).clickable {
                    if (viewState.isEditPackage) {
                        copyPackageIdToClipboard(context, viewState.packageIdText)
                    }
                }
        )

        Button(
            onClick = onAddButtonClicked,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = if (viewState.isEditPackage) {
                    stringResource(R.string.edit)
                } else {
                    stringResource(R.string.add)
                },
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