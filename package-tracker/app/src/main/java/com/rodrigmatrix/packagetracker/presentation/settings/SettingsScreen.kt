package com.rodrigmatrix.packagetracker.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.packagetracker.presentation.components.SettingWithText
import com.rodrigmatrix.packagetracker.presentation.components.SwitchWithDescription
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

@Composable
fun SettingsScreen() {
    Column(Modifier.fillMaxSize()) {
        val checked = remember { mutableStateOf(false) }

        SwitchWithDescription(
            checked = checked.value,
            description = "Receber Notificações de encomendas",
            null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 32.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
        )

        SettingWithText(
            title = "Intervalo de atualização",
            selectedSetting = "15 minutos",
            onClick = { },
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
        )

        SettingWithText(
            title = "Tema do aplicativo",
            selectedSetting = "Padrão do sistema",
            onClick = { },
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
        )
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun SettingsPreview() {
    PackageTrackerTheme {
        SettingsScreen()
    }
}