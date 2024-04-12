package com.rodrigmatrix.packagetracker.presentation.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.android.material.color.DynamicColors
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.components.MultiLayoutScaffold
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.DevicePosture
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenContentType
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenNavigationContentPosition
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenNavigationType
import com.rodrigmatrix.packagetracker.presentation.utils.isBookPosture
import com.rodrigmatrix.packagetracker.presentation.utils.isSeparating

class NavigationActivity : AppCompatActivity() {

    private val link by lazy {
        intent.extras?.getString("link").orEmpty()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openLink()
        setContent {
            val navController = rememberNavController()
            val windowSize = calculateWindowSizeClass(this)
            val displayFeatures = calculateDisplayFeatures(this)
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val navigationType: ScreenNavigationType
            val contentType: ScreenContentType

            /**
             * We are using display's folding features to map the device postures a fold is in.
             * In the state of folding device If it's half fold in BookPosture we want to avoid content
             * at the crease/hinge
             */
            val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

            val foldingDevicePosture = when {
                isBookPosture(foldingFeature) ->
                    DevicePosture.BookPosture(foldingFeature.bounds)

                isSeparating(foldingFeature) ->
                    DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                else -> DevicePosture.NormalPosture
            }

            when (windowSize.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    navigationType = ScreenNavigationType.BOTTOM_NAVIGATION
                    contentType = ScreenContentType.SINGLE_PANE
                }
                WindowWidthSizeClass.Medium -> {
                    navigationType = ScreenNavigationType.NAVIGATION_RAIL
                    contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                        ScreenContentType.DUAL_PANE
                    } else {
                        ScreenContentType.SINGLE_PANE
                    }
                }
                WindowWidthSizeClass.Expanded -> {
                    navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                        ScreenNavigationType.NAVIGATION_RAIL
                    } else {
                        ScreenNavigationType.NAVIGATION_RAIL
                    }
                    contentType = ScreenContentType.DUAL_PANE
                }
                else -> {
                    navigationType = ScreenNavigationType.BOTTOM_NAVIGATION
                    contentType = ScreenContentType.SINGLE_PANE
                }
            }

            /**
             * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
             * ergonomics and reachability depending upon the height of the device.
             */
            val navigationContentPosition = when (windowSize.heightSizeClass) {
                WindowHeightSizeClass.Compact -> {
                    ScreenNavigationContentPosition.TOP
                }
                WindowHeightSizeClass.Medium,
                WindowHeightSizeClass.Expanded -> {
                    ScreenNavigationContentPosition.CENTER
                }
                else -> {
                    ScreenNavigationContentPosition.TOP
                }
            }
            PackageTrackerTheme {
                MultiLayoutScaffold(
                    windowSizeClass = windowSize,
                    bottomBar = {
                        HomeBottomBar(navController)
                    },
                    floatingActionButton = {

                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    navigationRail = {
                        NavigationRail(
                            header = {

                            },
                            modifier = Modifier.safeDrawingPadding()
                        ) {
                            HomeRoutes.entries.forEach { destination ->
                                val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                                NavigationRailItem(
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(destination.route) {
                                            restoreState = true
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            destination.image,
                                            contentDescription = null
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(destination.resourceId),
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                )
                            }
                        }
                    },
                    navigationDrawer = {
                        NavigationRail(
                            header = { },
                            modifier = Modifier.width(230.dp)
                        ) {
                            HomeRoutes.entries.forEach { destination ->
                                val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                                NavigationDrawerItem(
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(destination.route) {
                                            restoreState = true
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            destination.image,
                                            contentDescription = null
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(destination.resourceId),
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                ) {
                    HomeNavHost(
                        navigationType,
                        contentType,
                        navController,
                        supportFragmentManager
                    )
                }
            }
        }
    }

    private fun openLink() {
        if (link.isNotEmpty()) {
            runCatching {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            }
        }
    }
}