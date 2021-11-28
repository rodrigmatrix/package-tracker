package com.rodrigmatrix.packagetracker.presentation.history

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.packagetracker.presentation.theme.*
import com.rodrigmatrix.packagetracker.presentation.theme.md_theme_light_primary

@Composable
fun PackageStatus(
    progressStatus: PackageProgressStatus
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(16.dp)
    ) {
        ConstraintLayout {
            val (
                dispatched, inProgress, done
            ) = createRefs()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(getEnabledAlpha(progressStatus.mailed))
                    .constrainAs(dispatched) {
                        start.linkTo(parent.start, 16.dp)

                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.FlightTakeoff,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(getEnabledColor(progressStatus.mailed))
                        .padding(8.dp)
                )
                
                Text(
                    text = "Despache",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(getEnabledAlpha(progressStatus.inProgress))
                    .constrainAs(inProgress) {
                        start.linkTo(dispatched.end, 16.dp)

                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocalShipping,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(getEnabledColor(progressStatus.inProgress))
                        .padding(8.dp)
                )

                Text(
                    text = "A caminho",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(getEnabledAlpha(progressStatus.delivered))
                    .constrainAs(done) {
                        start.linkTo(inProgress.end, 16.dp)
                        end.linkTo(parent.end, 16.dp)

                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(getEnabledColor(progressStatus.delivered))
                        .padding(8.dp)
                )

                Text(
                    text = "Entregue",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp
                )
            }
        }
    }
}

private fun getEnabledAlpha(enabled: Boolean): Float {
    return if (enabled) 1f else 0.4f
}

private fun getEnabledColor(isEnabled: Boolean): Color {
    return if (isEnabled) theme_light_done else theme_light_disabled
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackagePreview() {
    val packageProgressStatus = PackageProgressStatus(
        mailed = true,
        inProgress = true,
        delivered = false
    )

    PackageTrackerTheme {
        PackageStatus(
            packageProgressStatus
        )
    }
}