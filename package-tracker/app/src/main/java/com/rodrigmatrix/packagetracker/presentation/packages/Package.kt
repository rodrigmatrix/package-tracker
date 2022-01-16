package com.rodrigmatrix.packagetracker.presentation.packages

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.extensions.getLastStatus
import com.rodrigmatrix.packagetracker.extensions.getStatusIconAndColor
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

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ).combinedClickable(
                onLongClick = {
                    onLongClick(packageItem.packageId)
                },
                onClick = {
                    if (statusUpdate == null) {
                        onLongClick(packageItem.packageId)
                    } else {
                        onItemClick(packageItem.packageId)
                    }
                }
            )
    ) {
        Row {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(statusColor)
                    .padding(8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                Text(
                    text = packageItem.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = lastStatus.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = statusUpdate?.getDateWithHour() ?: "-",
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = packageItem.packageId,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
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