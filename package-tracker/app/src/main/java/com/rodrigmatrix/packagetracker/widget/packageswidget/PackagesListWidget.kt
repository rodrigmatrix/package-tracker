package com.rodrigmatrix.packagetracker.widget.packageswidget

import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.R
import java.io.File

@Composable
fun PackagesListWidget(
    userPackageList: List<UserPackage>,
    onWidgetClicked: () -> Unit,
    glanceModifier: GlanceModifier = GlanceModifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = glanceModifier.background(GlanceTheme.colors.background)
    ) {
        Spacer(GlanceModifier.height(8.dp))
        LazyColumn {
            items(userPackageList) { userPackage ->
                UserPackageItem(
                    userPackage = userPackage,
                    glanceModifier = glanceModifier.clickable(block = onWidgetClicked),
                )
            }
        }
        Spacer(GlanceModifier.height(16.dp))
    }
}

@Composable
private fun UserPackageItem(
    userPackage: UserPackage,
    glanceModifier: GlanceModifier = GlanceModifier,
) {
    Row(
        verticalAlignment = Alignment.Vertical.CenterVertically,
        modifier = glanceModifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        val image = when {
            userPackage.imagePath?.isNotEmpty() == true -> {
                val imgFile = File(userPackage.imagePath.orEmpty())
                ImageProvider(BitmapFactory.decodeFile(imgFile.absolutePath))
            }
            userPackage.iconType?.isNotEmpty() == true -> {
                //ImageProvider(getIconOptions().find { it.type == userPackage.iconType.orEmpty() }?.icon)
                ImageProvider(R.drawable.ic_launcher_foreground)
            }
            else -> {
                ImageProvider(R.drawable.ic_launcher_foreground)
                //ImageProvider(userPackage.statusUpdateList.firstOrNull().getStatusIconAndColor().second)
            }
        }
        Image(
            provider = image,
            contentDescription = null,
            modifier = GlanceModifier.size(36.dp)
        )
        Spacer(modifier = GlanceModifier.defaultWeight())
        Column {
            Text(
                text = userPackage.name,
                maxLines = 1,
                style = TextStyle(
                    color = GlanceTheme.colors.onPrimaryContainer,
                    fontSize = 16.sp,
                )
            )
            Text(
                text = userPackage.statusUpdateList.firstOrNull()?.title.orEmpty(),
                maxLines = 1,
                style = TextStyle(
                    color = GlanceTheme.colors.onPrimaryContainer,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}

@Composable
fun MediumLargeLoading(
    onWidgetClicked: () -> Unit,
    modifier: GlanceModifier = GlanceModifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(ImageProvider(R.drawable.package_outside_shape_widget))
            .fillMaxSize()
            .clickable(block = onWidgetClicked)
    ) {
        Text(
            text = "Loading Data...",
            style = TextStyle(
                color = GlanceTheme.colors.onPrimaryContainer,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@OptIn(ExperimentalGlancePreviewApi::class)
@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun PackagesListWidgetPreview() {
    GlanceTheme {
        PackagesListWidget(
            userPackageList = PreviewPackagesList,
            onWidgetClicked = { },
        )
    }
}

val PreviewPackagesList = listOf(
    UserPackage(
        packageId = "",
        name = "Test",
        iconType = "",
        imagePath = "",
        postalDate = "",
        deliveryType = "",
        status = PackageProgressStatus(
            mailed = false,
            inProgress = false,
            delivered = false,
            outForDelivery = false,
        ),
        statusUpdateList = listOf(
            StatusUpdate(title = "Test")
        ),
    ),
    UserPackage(
        packageId = "",
        name = "Test",
        iconType = "",
        imagePath = "",
        postalDate = "",
        deliveryType = "",
        status = PackageProgressStatus(
            mailed = false,
            inProgress = false,
            delivered = false,
            outForDelivery = false,
        ),
        statusUpdateList = listOf(
            StatusUpdate(title = "Test")
        ),
    ),
)