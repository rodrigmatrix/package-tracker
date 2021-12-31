package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rodrigmatrix.packagetracker.R

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