package com.rodrigmatrix.rastreio.presentation.packages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import org.koin.androidx.compose.getViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.rodrigmatrix.rastreio.extensions.getLastStatus

@Composable
fun PackagesScreen(
    viewModel: PackagesViewModel = getViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Surface {
        Column {
            SwipeRefresh(
                state = viewState.isRefreshing,
                onRefresh = { viewModel.loadPackages(forceUpdate = true) }
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                ) {
                    when  {
                        viewState.exception != null -> {

                        }

                        viewState.packagesList.isNotEmpty() ->
                            PackagesList(viewState.packagesList)

                        else -> PackagesEmptyState()
                    }
                }
            }
        }
    }
}

@Composable
fun PackagesList(packagesList: List<UserPackageAndUpdates>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(packagesList) { packageItem ->
            PackageItem(packageItem)
        }
    }
}

@Composable
fun PackageItem(packageItem: UserPackageAndUpdates) {
    val lastStatus = packageItem.getLastStatus()
    ConstraintLayout(
        modifier = Modifier.clickable { /* TODO */ }
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

        // If we have an image Url, we can show it using Coil
        Image(
            imageVector = Icons.Filled.PlaylistAdd,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
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
                text = packageItem.id,
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