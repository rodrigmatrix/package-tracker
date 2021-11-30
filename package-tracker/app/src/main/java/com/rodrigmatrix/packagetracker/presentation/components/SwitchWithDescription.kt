package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SwitchWithDescription(
    checked: Boolean,
    description: String,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = description,
            modifier = Modifier.fillMaxWidth()
        )

        Switch(
            checked,
            onCheckedChange
        )
    }
}