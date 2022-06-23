package com.rodrigmatrix.packagetracker.extensions

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun WindowSizeClass.isCompact(): Boolean {
    return widthSizeClass == WindowWidthSizeClass.Compact
}

fun WindowSizeClass.isMedium(): Boolean {
    return widthSizeClass == WindowWidthSizeClass.Medium
}

fun WindowSizeClass.isExpanded(): Boolean {
    return widthSizeClass == WindowWidthSizeClass.Expanded
}