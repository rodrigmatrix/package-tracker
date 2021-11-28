package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(text: String) {
    android.widget.Toast
        .makeText(LocalContext.current, text, android.widget.Toast.LENGTH_SHORT)
        .show()
}