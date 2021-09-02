package com.rodrigmatrix.rastreio.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
            modifier = Modifier
                .clip(CircleShape)
                .constrainAs(icon) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
                .background(statusColor)
                .padding(4.dp)
                .size(32.dp)

        )

        Column(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(textItems) {
                    start.linkTo(icon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = statusUpdate.description,
                color = statusColor
            )
            Text(text = statusUpdate.date)
            Text(text = "De: ${statusUpdate.from.localName}")
            Text(text = "Para: ${statusUpdate.to?.localName}")
        }




    }


}