package com.rodrigmatrix.packagetracker.presentation.history

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.packagetracker.extensions.getStatusIconAndColor
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewAllTypes

@Composable
fun PackageUpdatesList(statusUpdateList: List<StatusUpdate>) {
    LazyColumn {
        items(statusUpdateList) { statusUpdate ->
            PackageUpdate(statusUpdate)
        }
    }
}

@Composable
fun PackageUpdate(statusUpdate: StatusUpdate) {
    val (statusColor, iconVector) = statusUpdate.getStatusIconAndColor()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(statusColor)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = statusUpdate.title,
                color = statusColor,
                style = MaterialTheme.typography.headlineSmall,
            )
            if (statusUpdate.description.isNotEmpty()) {
                Text(
                    text = statusUpdate.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            statusUpdate.getDateWithHour()?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Address(statusUpdate.from, isDestination = false)
            Address(statusUpdate.to, isDestination = true)
        }
    }
}

@Composable
fun Address(address: StatusAddress?, isDestination: Boolean) {
    address?.getLocationText()?.let {
        val iconVector = if (isDestination) {
            Icons.Outlined.LocalShipping
        } else {
            Icons.Outlined.LocationOn
        }
        Row {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(16.dp)
            )

            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun StatusAddress.getLocationText(): String? {
    return when {
        unitType?.contains("Unidade") == true -> when {
            city.isNotEmpty() && state.isNotEmpty() -> "$unitType:$city-$state"
            localName.isEmpty() -> unitType
            else -> "$unitType:$localName"
        }
        localName.isNotEmpty() -> localName
        city.isNotEmpty() && state.isEmpty() -> city
        city.isEmpty() && state.isNotEmpty() -> state
        city.isEmpty() && state.isEmpty() -> null
        else -> "$city - $state"
    }
}


@PreviewAllTypes
@Composable
fun PackageUpdatePreview() {
    val packageItem = StatusUpdate(
        date = "20/07/2022",
        title = "Saiu para entrega",
        from = StatusAddress()
    )

    PackageTrackerTheme {
        PackageUpdate(packageItem)
    }
}