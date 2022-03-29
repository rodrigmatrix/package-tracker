package com.rodrigmatrix.packagetracker.presentation.packages

import com.rodrigmatrix.packagetracker.presentation.packages.viewmodel.PackagesViewState
import com.rodrigmatrix.packagetracker.presentation.stub.PackageTestStubs.deliveredPackage
import com.rodrigmatrix.packagetracker.presentation.stub.PackageTestStubs.inProgressPackage
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import org.junit.Test

internal class PackagesScreenTest : PackagesScreenRobot() {

    private fun createScreen(viewState: PackagesViewState) {
        composeTestRule.setContent {
            PackageTrackerTheme {
                PackagesScreen(
                    viewState = viewState,
                    onSwipeRefresh = { },
                    onAddPackageClick = { },
                    onPackageClick = { },
                    onLongClick = { },
                    onConfirmDeletePackage = { },
                    onDismissDeletePackageDialog = { }
                )
            }
        }
    }

    @Test
    fun givenSuccessState_Then_DisplayPackagesList() {
        // Given
        val viewState = PackagesViewState(
            packagesList = listOf(inProgressPackage, deliveredPackage)
        )

        // When
        createScreen(viewState)

        // Then
        checkCardItemDisplayed("Google Pixel 6")
    }
}