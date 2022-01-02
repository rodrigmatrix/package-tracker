package com.rodrigmatrix.packagetracker.presentation.packages

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.rodrigmatrix.packagetracker.presentation.stub.PackageTestStubs
import com.rodrigmatrix.packagetracker.presentation.stub.PackageTestStubs.deliveredPackage
import com.rodrigmatrix.packagetracker.presentation.stub.PackageTestStubs.inProgressPackage
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewPackageItemsList
import org.junit.Rule
import org.junit.Test

class PackagesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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
        composeTestRule.onNodeWithText("Google Pixel 4").assertIsDisplayed()
        composeTestRule
            .onNodeWithTag(PACKAGES_LIST_TAG)
            .onChild()
            .onChildren()
            .assertCountEquals(2)
    }

}