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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.packagetracker.extensions.getStatusIconAndColor
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

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

    ConstraintLayout(Modifier.fillMaxWidth()) {
        val (icon, textItems) = createRefs()

        Icon(
            imageVector = iconVector,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(statusColor)
                .padding(8.dp)
                .constrainAs(icon) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(textItems) {
                    start.linkTo(icon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            Text(
                text = statusUpdate.description,
                color = statusColor,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp
            )
            Text(
                text = statusUpdate.date,
                style = MaterialTheme.typography.bodySmall
            )
            Address(statusUpdate.from, isDestination = false)
            Address(statusUpdate.to, isDestination = true)
        }
    }
}

@Composable
fun Address(address: StatusAddress?, isDestination: Boolean) {
    address ?: return

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
            text = "${address.localName}-${address.state}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Large Font", fontScale = 2f)
@Composable
fun PackageUpdatePreview() {
    val packageItem = StatusUpdate(
        date = "20/07/2022",
        description = "Saiu para entrega",
        from = StatusAddress()
    )

    PackageTrackerTheme {
        PackageUpdate(packageItem)
    }
}