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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.material.color.DynamicColors
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.packagetracker.presentation.components.MultiLayoutScaffold
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

class NavigationActivity : AppCompatActivity() {

    private val link by lazy {
        intent.extras?.getString("link").orEmpty()
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyIfAvailable(this)
        openLink()
        setContent {
            val navController = rememberNavController()
            val windowSizeClass = calculateWindowSizeClass(this)
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            PackageTrackerTheme {
                MultiLayoutScaffold(
                    windowSizeClass = windowSizeClass,
                    bottomBar = {
                        HomeBottomBar(navController)
                    },
                    floatingActionButton = {
                        if (currentDestination?.route == HomeRoutes.Packages.route) {
                            AddPackageFab(windowSizeClass = windowSizeClass)
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    navigationRail = {
                        NavigationRail(
                            header = {
                                AddPackageFab(windowSizeClass = windowSizeClass)
                            },
                            modifier = Modifier.safeDrawingPadding()
                        ) {
                            HomeRoutes.values().forEach { destination ->
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
                            header = {
                                AddPackageFab(windowSizeClass = windowSizeClass)
                            },
                            modifier = Modifier.width(230.dp)
                        ) {
                            HomeRoutes.values().forEach { destination ->
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
                        windowSizeClass,
                        navController,
                        supportFragmentManager
                    )
                }
            }
        }
    }

    @Composable
    private fun AddPackageFab(windowSizeClass: WindowSizeClass) {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                LargeFloatingActionButton(
                    onClick = {
                        AddNewPackageBottomSheetFragment()
                            .show(supportFragmentManager, "")
                    },
                    shape = RoundedCornerShape(100)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(24.dp),
                        contentDescription = getString(R.string.add_package)
                    )
                }
            }
            WindowWidthSizeClass.Medium -> {
                FloatingActionButton(
                    onClick = {
                        AddNewPackageBottomSheetFragment()
                            .show(supportFragmentManager, "")
                    },
                    shape = RoundedCornerShape(100)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(24.dp),
                        contentDescription = getString(R.string.add_package)
                    )
                }
            }
            WindowWidthSizeClass.Expanded -> {
                FloatingActionButton(
                    onClick = {
                        AddNewPackageBottomSheetFragment()
                            .show(supportFragmentManager, "")
                    },
                    shape = CircleShape
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            modifier = Modifier.size(24.dp),
                            contentDescription = getString(R.string.add_package)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = stringResource(R.string.add_package))
                    }
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