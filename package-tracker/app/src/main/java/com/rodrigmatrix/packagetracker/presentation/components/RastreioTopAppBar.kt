@file:OptIn(ExperimentalMaterial3Api::class)

package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

@Composable
fun PackageTrackerTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Box(modifier = Modifier) {
        TopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            navigationIcon = navigationIcon,
        )
    }
}

@Preview
@Composable
fun PackageTrackerAppBarPreview() {
    PackageTrackerTheme {
        PackageTrackerTopAppBar(title = { Text("Preview!") }, navigationIcon =  {
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        })
    }
}

@Preview(name = "")
@Composable
fun PackageTrackerAppBarPreviewDark() {
    PackageTrackerTheme {
        PackageTrackerTopAppBar(title = { Text("Preview!") }, navigationIcon =  {
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        })
    }
}