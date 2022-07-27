package com.rodrigmatrix.packagetracker.presentation.settings

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import com.rodrigmatrix.core.extensions.toast
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.extensions.ComposableLifecycle
import com.rodrigmatrix.packagetracker.presentation.components.SettingWithText
import com.rodrigmatrix.packagetracker.presentation.components.SingleChoiceSettingDialog
import com.rodrigmatrix.packagetracker.presentation.components.SwitchWithDescription
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsViewEffect.ShowToast
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.LaunchViewEffect
import com.rodrigmatrix.packagetracker.presentation.utils.ThemeUtils
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel(),
    themeUtils: ThemeUtils = get()
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchViewEffect(viewModel) { viewEffect ->
        when (viewEffect) {
            is ShowToast -> {
                context.toast(viewEffect.message)
            }
            is SettingsViewEffect.UpdateTheme -> {
                themeUtils.setTheme()
            }
            SettingsViewEffect.OpenNotificationsSettings -> {
                val intent = Intent().apply {
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        }
                        else -> {
                            action = "android.settings.APP_NOTIFICATION_SETTINGS"
                            putExtra("app_package", context.packageName)
                            putExtra("app_uid", context.applicationInfo.uid)
                        }
                    }
                }
                context.startActivity(intent)
            }
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
        onNotificationDialogDismiss = viewModel::hideNotificationsDialog,
        onOpenSystemNotificationSettings = viewModel::onSystemNotificationsPreferenceClicked
    )
    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            val enabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
            viewModel.onSystemNotificationsPreferenceChanged(enabled)
        }
    }
}

@Composable
fun SettingsScreen(
    viewState: SettingsViewState,
    onThemeDialogDismiss: () -> Unit,
    onThemeSelected: (String) -> Unit,
    onOpenThemeDialog: () -> Unit,
    onNotificationOptionChanged: (Boolean, Int) -> Unit,
    onOpenNotificationsDialog: () -> Unit,
    onNotificationDialogDismiss: () -> Unit,
    onOpenSystemNotificationSettings: (Int) -> Unit
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
        SwitchWithDescription(
            checked = viewState.notificationsEnabled,
            description = stringResource(R.string.enable_notifications),
            onCheckedChange = {
                onNotificationOptionChanged(it, 0)
            }
        )
        if (viewState.systemNotificationsEnabled.not()) {
            ClickableText(
                text = buildAnnotatedString {
                    pushStyle(style = SpanStyle(color = Color.Red))
                    append(stringResource(R.string.enable_system_notifications))
                },
                style = MaterialTheme.typography.titleMedium,
                onClick = onOpenSystemNotificationSettings,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
            )
        }
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
            onThemeDialogDismiss = {},
            onThemeSelected = {},
            onOpenThemeDialog = {},
            onNotificationOptionChanged = { _, _ -> },
            onOpenNotificationsDialog = {},
            onNotificationDialogDismiss = {},
            onOpenSystemNotificationSettings = {}
        )
    }
}