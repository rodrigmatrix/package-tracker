package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaveProgress(
    value: Float,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showWave: Boolean = true,
    enableAnimation: Boolean = true,
    steps: Int = 0,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
) {
    val colorScheme = MaterialTheme.colorScheme
    val amplitude = if (showWave) 15f else 0f
    val frequency = 0.07f
    val trackWidth = 8f
    val animationSpeedMs = 10000

    var isDragging by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is DragInteraction.Start -> {
                    isDragging = true
                }

                is DragInteraction.Stop -> {
                    isDragging = false
                }
            }
        }
    }
    val infiniteTransition = rememberInfiniteTransition(label = "Wave infinite transition")
    val phaseShiftFloat = infiniteTransition.animateFloat(
        label = "Wave phase shift",
        initialValue = 0F,
        targetValue = frequency * 1000 * 4 * 3.14f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationSpeedMs
            },
            repeatMode = RepeatMode.Restart
        )
    ).value
    Slider(
        steps = steps,
        value = value,
        onValueChangeFinished = { },
        onValueChange = { },
        interactionSource = interactionSource,
        enabled = enabled,
        modifier = modifier,
        thumb = { },
        track = { _ ->
            val animatedAmplitude = animateFloatAsState(
                targetValue = amplitude,
                label = "Wave amplitude"
            ).value
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val centerY = size.height / 2f
                val startX = -26f
                val endX = (size.width * value) + 26f
                val path = Path()
                for (x in startX.toInt()..endX.toInt()) {
                    var modifiedX = x.toFloat()
                    if (enableAnimation && enabled) {
                        modifiedX -= phaseShiftFloat
                    }
                    val y = (animatedAmplitude * sin(frequency * modifiedX))
                    path.moveTo(x.toFloat(), centerY - y)
                    path.lineTo(x.toFloat(), centerY - y)
                }
                drawPath(
                    path = path,
                    color = if (enabled) {
                        primaryColor
                    } else {
                        colorScheme.secondaryContainer
                    },
                    style = Stroke(
                        width = trackWidth,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    )
}