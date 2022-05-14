package com.rodrigmatrix.packagetracker.presentation.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.material.color.DynamicColors
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
class NavigationActivity : AppCompatActivity() {

    private val link by lazy {
        intent.extras?.getString("link").orEmpty()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyIfAvailable(this)
        openLink()
        setContent {
            val navController = rememberNavController()
            val windowSizeClass = calculateWindowSizeClass(this)
            PackageTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                            HomeBottomBar(navController)
                        }
                    },
                ) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
                    ) {
                        if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            NavigationRail(modifier = Modifier.safeDrawingPadding()) {
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
                                    label = { Text(text = stringResource(destination.resourceId)) }
                                )
                            }
                            }
                        }
                        HomeNavHost(
                            windowSizeClass,
                            navController,
                            supportFragmentManager
                        )
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