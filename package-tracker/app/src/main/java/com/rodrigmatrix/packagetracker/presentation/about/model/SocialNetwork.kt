package com.rodrigmatrix.packagetracker.presentation.about.model

import androidx.annotation.DrawableRes
import com.rodrigmatrix.packagetracker.R

enum class SocialNetwork(
    val link: String,
    @DrawableRes val icon: Int
) {

    GITHUB(
        link = "https://github.com/rodrigmatrix",
        icon = R.drawable.ic_github
    ),
    TWITTER(
        link = "https://twitter.com/rodrigmatrix",
        icon = R.drawable.ic_twitter
    ),
    LINKEDIN(
        link = "https://www.linkedin.com/in/rodrigmatrix",
        icon = R.drawable.ic_linkedin
    ),
}