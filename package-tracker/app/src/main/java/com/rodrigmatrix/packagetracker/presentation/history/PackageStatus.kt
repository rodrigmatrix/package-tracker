package com.rodrigmatrix.packagetracker.presentation.history

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FlightTakeoff
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.packagetracker.presentation.components.WaveProgress
import com.rodrigmatrix.packagetracker.presentation.theme.*

@Composable
fun PackageStatus(
    progressStatus: PackageProgressStatus,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (
            dispatched, inProgress, done, dispatchedWave, inProgressWave,
            dispatchedText, inProgressText, doneText,
        ) = createRefs()
        var dispatchedWaveProgress by remember {
            mutableFloatStateOf(-20f)
        }
        var doneWaveProgress by remember {
            mutableFloatStateOf(-20f)
        }
        LaunchedEffect(Unit) {
            animate(
                initialValue = -20f,
                targetValue = 1f,
                animationSpec = tween(800),
            ) { value, _ ->
                dispatchedWaveProgress = value
            }

            if (dispatchedWaveProgress == 1f) {
                animate(
                    initialValue = -20f,
                    targetValue = 1f,
                    animationSpec = tween(800),
                ) { value, _ ->
                    doneWaveProgress = value
                }
            }
        }
        Icon(
            imageVector = Icons.Filled.FlightTakeoff,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(getEnabledColor(progressStatus.mailed))
                .padding(8.dp)
                .constrainAs(dispatched) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(inProgress.start, 52.dp)

                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = "Despache",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.mailed))
                .constrainAs(dispatchedText) {
                top.linkTo(dispatched.bottom, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
                start.linkTo(dispatched.start)
                end.linkTo(dispatched.end)
            }
        )
        WaveProgress(
            value = dispatchedWaveProgress,
            primaryColor = getEnabledColor(progressStatus.inProgress),
            showWave = !progressStatus.inProgress,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.inProgress))
                .constrainAs(dispatchedWave) {
                    start.linkTo(dispatched.end)
                    end.linkTo(inProgress.start)
                    top.linkTo(dispatched.top)
                    bottom.linkTo(dispatched.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        Icon(
            imageVector = Icons.Filled.LocalShipping,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.inProgress))
                .size(34.dp)
                .clip(CircleShape)
                .background(getEnabledColor(progressStatus.inProgress))
                .padding(8.dp)
                .constrainAs(inProgress) {
                    start.linkTo(dispatched.end)
                    end.linkTo(done.start, 52.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = "A caminho",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.inProgress))
                .constrainAs(inProgressText) {
                top.linkTo(inProgress.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(inProgress.start)
                end.linkTo(inProgress.end)
            }
        )

        WaveProgress(
            value = doneWaveProgress,
            primaryColor = getEnabledColor(progressStatus.delivered),
            showWave = !progressStatus.delivered,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.delivered))
                .constrainAs(inProgressWave) {
                    start.linkTo(inProgress.end)
                    end.linkTo(done.start)
                    top.linkTo(inProgress.top)
                    bottom.linkTo(inProgress.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.delivered))
                .size(34.dp)
                .clip(CircleShape)
                .background(getEnabledColor(progressStatus.delivered))
                .padding(8.dp)
                .constrainAs(done) {
                    start.linkTo(inProgress.end)
                    end.linkTo(parent.end, 8.dp)

                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = "Entregue",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .alpha(getEnabledAlpha(progressStatus.delivered))
                .constrainAs(doneText) {
                top.linkTo(done.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(done.start)
                end.linkTo(done.end)
            }
        )
    }
}

private fun getEnabledAlpha(enabled: Boolean): Float {
    return if (enabled) 1f else 0.4f
}

private fun getEnabledColor(isEnabled: Boolean): Color {
    return if (isEnabled) theme_light_done else theme_light_done_variant
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun PackagePreview() {
    val packageProgressStatus = PackageProgressStatus(
        mailed = true,
        inProgress = true,
        delivered = false,
        outForDelivery = false,
    )

    PackageTrackerTheme {
        PackageStatus(
            packageProgressStatus
        )
    }
}