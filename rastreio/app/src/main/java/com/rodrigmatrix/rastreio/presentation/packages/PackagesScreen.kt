package com.rodrigmatrix.rastreio.presentation.packages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.rastreio.extensions.getLastStatus
import com.rodrigmatrix.rastreio.extensions.getStatusIconAndColor

@Composable
fun PackagesScreen(
    viewModel: PackagesViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Column {
            SwipeRefresh(
                state = viewState.isRefreshing,
                onRefresh = { viewModel.fetchPackages() }
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                ) {
                    PackagesList(viewModel, viewState.packagesList)
                }
            }
        }
    }
}

@Composable
fun PackagesList(
    viewModel: PackagesViewModel,
    packagesList: List<UserPackage>
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(packagesList) { packageItem ->
            PackageItem(viewModel, packageItem)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PackageItem(
    viewModel: PackagesViewModel,
    packageItem: UserPackage
) {
    val lastStatus = packageItem.getLastStatus()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { viewModel.openPackage(packageItem.packageId) },
                onLongClick = { viewModel.deletePackage(packageItem.packageId) }
            )
    ) {
        val (
            divider, episodeTitle, podcastTitle, image
        ) = createRefs()

        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)

                width = Dimension.fillToConstraints
            }
        )

        val statusUpdate = packageItem.statusUpdate.orEmpty().first()

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
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                },
        )

        Text(
            text = packageItem.name,
            maxLines = 2,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(episodeTitle) {
                linkTo(
                    start = parent.start,
                    end = image.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, 16.dp)

                width = Dimension.preferredWrapContent
            }
        )

        val titleImageBarrier = createBottomBarrier(podcastTitle, image)

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = packageItem.packageId,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.constrainAs(podcastTitle) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        startMargin = 24.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(episodeTitle.bottom, 6.dp)

                    width = Dimension.preferredWrapContent
                },
                color = lastStatus.color
            )
        }

    }
}

@Composable
fun PackagesEmptyState() {
    Column {



    }
}