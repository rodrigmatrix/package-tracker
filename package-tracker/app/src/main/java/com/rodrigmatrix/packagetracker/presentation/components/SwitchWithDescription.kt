package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SwitchWithDescription(
    checked: Boolean,
    description: String,
    onCheckedChange: ((Boolean) -> Unit),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable {
            onCheckedChange(!checked)
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        )
        val icon: (@Composable () -> Unit)? = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            thumbContent = icon,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}