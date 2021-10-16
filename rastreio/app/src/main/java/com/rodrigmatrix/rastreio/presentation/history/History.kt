package com.rodrigmatrix.rastreio.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.rastreio.extensions.getStatusIconAndColor

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

    ConstraintLayout {
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
                }
        ) {
            Text(
                text = statusUpdate.description,
                color = statusColor,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = statusUpdate.date,
                style = MaterialTheme.typography.caption
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
                .align(Alignment.CenterVertically)
        )

        Text(
            text = "${address.localName}-${address.state}",
            style = MaterialTheme.typography.body2
        )
    }
}