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
import androidx.compose.ui.tooling.preview.Preview
import com.rodrigmatrix.packagetracker.presentation.theme.RastreioTheme

@Composable
fun RastreioTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    Box(modifier = Modifier.background(backgroundColor)) {
        SmallTopAppBar(
            modifier = modifier,
            actions = actions,
            title = title,
            navigationIcon = navigationIcon
        )
    }
}

@Preview
@Composable
fun RastreioAppBarPreview() {
    RastreioTheme {
        RastreioTopAppBar(title = { Text("Preview!") }, navigationIcon =  {
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        })
    }
}

@Preview(name = "")
@Composable
fun RastreioAppBarPreviewDark() {
    RastreioTheme {
        RastreioTopAppBar(title = { Text("Preview!") }, navigationIcon =  {
            IconButton(onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        })
    }
}