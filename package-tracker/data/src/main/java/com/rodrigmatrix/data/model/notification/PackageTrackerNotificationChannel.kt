package com.rodrigmatrix.data.model.notification

import androidx.annotation.StringRes
import com.rodrigmatrix.data.R

enum class
PackageTrackerNotificationChannel(
    val id: String,
    @StringRes val titleStringRes: Int,
    @StringRes val descriptionStringRes: Int
) {

    GENERAL(
        id = "package_tracker_package_general_channel",
        titleStringRes = R.string.general_channel_name,
        descriptionStringRes = R.string.general_channel_description
    ),
    PACKAGE_UPDATES(
        id = "package_tracker_package_updates_channel",
        titleStringRes = R.string.package_updates_channel_name,
        descriptionStringRes = R.string.package_updates_channel_description
    )
}