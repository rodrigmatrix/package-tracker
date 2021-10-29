package com.rodrigmatrix.packagetracker.presentation.packages

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.extensions.getLastStatus
import com.rodrigmatrix.packagetracker.extensions.getStatusIconAndColor
import com.rodrigmatrix.packagetracker.presentation.theme.RastreioTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Package(
    onItemClick: (id: String) -> Unit,
    packageItem: UserPackage
) {
    val lastStatus = packageItem.getLastStatus()

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onItemClick(packageItem.packageId)
            }
    ) {
        ConstraintLayout {
            val (
                name, lastUpdate, image, packageId
            ) = createRefs()

            val statusUpdate = packageItem.statusUpdateList.orEmpty().first()

            val (statusColor, iconVector) = statusUpdate.getStatusIconAndColor()

            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(statusColor)
                    .padding(8.dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                    },
            )

            Text(
                text = packageItem.name,
                maxLines = 2,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.constrainAs(name) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = 8.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(parent.top, 16.dp)
                    width = Dimension.preferredWrapContent
                }
            )

            Text(
                text = "${lastStatus.title} - ${statusUpdate.date}",
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(lastUpdate) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = 8.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(name.bottom)
                }
            )
            Text(
                text = packageItem.packageId,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(packageId) {
                    linkTo(
                        start = image.end,
                        end = parent.end,
                        startMargin = 8.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(lastUpdate.bottom, 6.dp)
                    bottom.linkTo(parent.bottom, 16.dp)

                    width = Dimension.preferredWrapContent
                }
            )
        }
    }
}

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackagePreview() {
    val packageItem = UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 4",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
            date = "20/07/2022",
            description = "Saiu para entrega",
            from = StatusAddress()
        )
        )
    )

    RastreioTheme {
        Package(
            onItemClick = {},
            packageItem = packageItem
        )
    }
}