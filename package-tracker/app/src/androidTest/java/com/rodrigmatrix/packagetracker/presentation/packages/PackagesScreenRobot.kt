package com.rodrigmatrix.packagetracker.presentation.packages

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.rodrigmatrix.packagetracker.BaseRobot

abstract class PackagesScreenRobot : BaseRobot() {

    fun checkCardItemDisplayed(title: String) {
        composeTestRule
            .onNodeWithText(title)
            .assertIsDisplayed()
    }

    fun checkLazyCollumSize(tag: String, size: Int) {
        composeTestRule
            .onNodeWithTag(tag)
            .onChild()
            .onChildren()
            .assertCountEquals(size)
    }
}