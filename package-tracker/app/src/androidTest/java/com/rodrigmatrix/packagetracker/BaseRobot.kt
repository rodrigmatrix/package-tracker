package com.rodrigmatrix.packagetracker

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

abstract class BaseRobot {

    @get:Rule
    val composeTestRule = createComposeRule()

}