package com.rodrigmatrix.packagetracker.presentation.notification

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import org.koin.androidx.compose.getViewModel

@ExperimentalPermissionsApi
@Composable
fun NotificationPermissionScreen(
    modifier: Modifier = Modifier,
    permissionRequest: PermissionState,
    navController: NavController
) {
    when {
        permissionRequest.shouldShowRationale ||
        permissionRequest.permissionRequested ||
        permissionRequest.hasPermission-> {
            navController.navigateUp()
        }
        else -> NotificationPermissionScreen(
            onGrantPermissionClicked = permissionRequest::launchPermissionRequest,
            onDismiss = {
                navController.navigateUp()
            },
            modifier = modifier
        )
    }
}

@Composable
private fun NotificationPermissionScreen(
    onGrantPermissionClicked: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.phone_notification))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = Int.MAX_VALUE
    )
    Column(
        modifier = modifier.padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        Text(
            text = stringResource(R.string.want_to_enable_notifications),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.we_need_notification_permission),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onGrantPermissionClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.enable),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.not_now),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun NotificationPermissionScreenPreview() {
    PackageTrackerTheme {
        NotificationPermissionScreen(
            onGrantPermissionClicked = { },
            onDismiss = { },
            Modifier.fillMaxSize()
        )
    }
}