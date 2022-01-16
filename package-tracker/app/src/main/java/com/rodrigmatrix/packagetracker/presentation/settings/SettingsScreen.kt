package com.rodrigmatrix.packagetracker.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.SettingWithText
import com.rodrigmatrix.packagetracker.presentation.components.SingleChoiceSettingDialog
import com.rodrigmatrix.packagetracker.presentation.components.SwitchWithDescription
import com.rodrigmatrix.packagetracker.presentation.components.Toast
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewEffect.ShowToast
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.ThemeUtils
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel(),
    themeUtils: ThemeUtils = get()
) {

    val viewState by viewModel.viewState.collectAsState()
    val viewEffect by viewModel.viewEffect.collectAsState(initial = null)

    when (viewEffect) {
        is ShowToast -> {
            Toast(text = (viewEffect as ShowToast).message)
        }
        is SettingsViewEffect.UpdateTheme -> {
            themeUtils.setTheme()
        }
        SettingsViewEffect.UpdateNotificationsInterval -> {

        }
    }

    SettingsScreen(
        viewState = viewState,
        onThemeDialogDismiss = viewModel::hideThemeDialog,
        onThemeSelected = { newTheme ->
            viewModel.setTheme(newTheme)
        },
        onOpenThemeDialog = viewModel::showThemeDialog,
        onNotificationOptionChanged = { checked, interval ->
            viewModel.onNewNotificationConfig(checked, interval)
        },
        onOpenNotificationsDialog = viewModel::showNotificationsDialog,
        onNotificationDialogDismiss = viewModel::hideNotificationsDialog
    )
}

@Composable
fun SettingsScreen(
    viewState: SettingsViewState,
    onThemeDialogDismiss: () -> Unit,
    onThemeSelected: (String) -> Unit,
    onOpenThemeDialog: () -> Unit,
    onNotificationOptionChanged: (Boolean, Int) -> Unit,
    onOpenNotificationsDialog: () -> Unit,
    onNotificationDialogDismiss: () -> Unit
) {

    if (viewState.themeDialogVisible) {
        SingleChoiceSettingDialog(
            title = stringResource(R.string.app_theme),
            options = viewState.themeOptionsList,
            onOptionSelected = onThemeSelected,
            onDismiss = onThemeDialogDismiss
        )
    }

    if (viewState.notificationIntervalDialogVisible) {
        SingleChoiceSettingDialog(
            title = stringResource(R.string.update_interval),
            options = viewState.notificationsIntervalOptionsList,
            onOptionSelected = { interval ->
                onNotificationOptionChanged(viewState.notificationsEnabled, interval)
            },
            onDismiss = onNotificationDialogDismiss
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp)
    ) {

        SettingWithText(
            title = stringResource(R.string.app_theme),
            selectedSetting = viewState.selectedTheme,
            onClick = onOpenThemeDialog,
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
        SettingsScreen(
            viewState = SettingsViewState(),
            {},
            {},
            {},
            { _, _ -> },
            {},
            {}
        )
    }
}