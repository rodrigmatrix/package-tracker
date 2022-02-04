package com.rodrigmatrix.packagetracker.presentation.navigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.material.color.DynamicColors
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
class NavigationActivity : AppCompatActivity() {

    private val link by lazy {
        intent.extras?.getString("link").orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyIfAvailable(this)
        openLink()
        setContent {
            val navController = rememberNavController()
            PackageTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        HomeBottomBar(navController)
                    }
                ) {
                    HomeNavHost(navController, supportFragmentManager)
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