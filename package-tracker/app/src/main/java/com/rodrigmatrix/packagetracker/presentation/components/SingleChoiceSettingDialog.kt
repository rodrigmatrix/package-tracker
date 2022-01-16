package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rodrigmatrix.domain.entity.SingleChoicePreference

@Composable
fun <T> SingleChoiceSettingDialog(
    title: String,
    options: List<SingleChoicePreference<T>>,
    onOptionSelected: (T) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)

                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(options) {
                        ChoiceItem(it, onOptionSelected)
                    }
                }
            }
        }
    }
}

@Composable
fun <T> ChoiceItem(
    choice: SingleChoicePreference<T>,
    onClick: (T) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = choice.selected,
                onClick = {
                    onClick(choice.value)
                }
            )
    ) {
        Text(
            text = choice.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
        )
        RadioButton(
            selected = choice.selected,
            onClick = {
                onClick(choice.value)
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}