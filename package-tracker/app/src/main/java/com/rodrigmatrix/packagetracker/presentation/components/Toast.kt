package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.rodrigmatrix.core.extensions.toast

@Composable
fun Toast(text: String) {
    LocalContext.current.toast(text)
}