package com.rodrigmatrix.packagetracker.presentation.packages

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.extensions.getCurrentStatusString
import com.rodrigmatrix.packagetracker.extensions.getDeliveryDateString
import com.rodrigmatrix.packagetracker.extensions.getLastStatus
import com.rodrigmatrix.packagetracker.extensions.getStatusIconAndColor
import com.rodrigmatrix.packagetracker.presentation.addpackage.getIconOptions
import com.rodrigmatrix.packagetracker.presentation.components.PackageImage
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItem


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Package(
    onItemClick: (id: String) -> Unit,
    onLongClick: (id: String) -> Unit,
    packageItem: UserPackage,
) {
    val lastStatus = packageItem.getLastStatus()

    val statusUpdate = packageItem.statusUpdateList.firstOrNull()

    val (statusColor, iconVector) = statusUpdate.getStatusIconAndColor()

    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = when {
                packageItem.status.delivered -> 1f
                packageItem.status.outForDelivery -> 0.9f
                packageItem.status.inProgress -> 0.5f
                packageItem.status.mailed -> 0.3f
                else -> 0.1f
            },
            animationSpec = tween(1000),
        ) { value, _ ->
            progress = value
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            )
            .combinedClickable(
                onLongClick = {
                    onLongClick(packageItem.packageId)
                },
                onClick = {
                    onItemClick(packageItem.packageId)
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 8.dp,
            )
        ) {
            if (packageItem.imagePath != null || packageItem.iconType != null) {
                PackageImage(
                    imagePath = packageItem.imagePath,
                    icon = getIconOptions().find { it.type == packageItem.iconType },
                )
            } else {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(statusColor)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = packageItem.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    color = MaterialTheme.colorScheme.tertiary,
                    strokeCap = StrokeCap.Round,
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (packageItem.status.delivered) {
                    Text(
                        text = packageItem.getDeliveryDateString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = packageItem.getCurrentStatusString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (lastStatus.description.isNotEmpty() && packageItem.status.mailed) {
                    Text(
                        text = lastStatus.description,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                if (!packageItem.status.delivered && packageItem.status.mailed) {
                    statusUpdate?.getDateWithHour()?.let {
                        Text(
                            text = it,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackagePreview() {

    PackageTrackerTheme {
        Package(
            onItemClick = { },
            onLongClick =  { },
            packageItem = PreviewPackageItem
        )
    }
}