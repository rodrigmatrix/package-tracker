package com.rodrigmatrix.packagetracker.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsScreen
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

@Composable
fun SettingWithText(
    title: String,
    selectedSetting: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = selectedSetting, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun SettingWithTextPreview() {
    PackageTrackerTheme {
        SettingWithText(
            title = "Intervalo de atualização",
            selectedSetting = "15 minutos"
        )
    }
}