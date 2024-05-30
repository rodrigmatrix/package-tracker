package com.rodrigmatrix.packagetracker.widget.packageswidget

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.packagetracker.presentation.navigation.NavigationActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PackagesListGlanceAppWidget: GlanceAppWidget(), KoinComponent {

    private val getAllPackagesUseCase by inject<GetAllPackagesUseCase>()

    private val mainScope: CoroutineScope = MainScope()

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                val state by getAllPackagesUseCase()
                    .catch { it }
                    .collectAsState(initial = null)
                if (state != null) {
                    PackagesListWidget(
                        userPackageList = state.orEmpty(),
                        onWidgetClicked = { openMainActivity(context) },
                    )
                }
            }
        }
    }

    private fun openMainActivity(context: Context) {
        Intent(context, NavigationActivity::class.java).setPackage(context.packageName).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }
    }

    fun updateWidget(context: Context) {
        mainScope.launch {
            updateAll(context)
        }
    }
}