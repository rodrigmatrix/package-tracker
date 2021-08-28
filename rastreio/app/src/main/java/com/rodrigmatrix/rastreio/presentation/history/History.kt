package com.rodrigmatrix.rastreio.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.domain.entity.StatusUpdate

@Composable
fun PackageUpdatesList(statusUpdateList: List<StatusUpdate>) {
    LazyColumn {
        items(statusUpdateList) { statusUpdate ->
            when (statusUpdate.userPackageId) {

            }
            RenderPackageUpdate(statusUpdate)
        }
    }
}

@Composable
fun RenderPackageUpdate(statusUpdate: StatusUpdate) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = statusUpdate.description)
        Text(text = "Data: ${statusUpdate.date}")
        Text(text = "De: ${statusUpdate.from.localName}")
        Text(text = "Para: ${statusUpdate.to?.localName}")
    }
}