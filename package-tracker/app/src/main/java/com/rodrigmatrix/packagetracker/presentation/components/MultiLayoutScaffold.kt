package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rodrigmatrix.packagetracker.extensions.isCompact

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MultiLayoutScaffold(
    windowSizeClass: WindowSizeClass,
    bottomBar: @Composable () -> Unit,
    navigationRail: @Composable () -> Unit,
    navigationDrawer: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = {
            if (windowSizeClass.isCompact()) {
                bottomBar()
            }
        },
        snackbarHost = snackbarHost,
        floatingActionButton = {
            if (windowSizeClass.isCompact()) {
                floatingActionButton()
            }
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor
    ) { innerPadding ->
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> content(innerPadding)
            WindowWidthSizeClass.Medium -> {
                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
                ) {
                    navigationRail()
                    content(innerPadding)
                }
            }
            WindowWidthSizeClass.Expanded -> {
                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
                ) {
                    navigationDrawer()
                    content(innerPadding)
                }
            }
        }
    }
}