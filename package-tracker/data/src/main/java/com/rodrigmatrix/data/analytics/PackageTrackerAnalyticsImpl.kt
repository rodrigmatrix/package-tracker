package com.rodrigmatrix.data.analytics

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.rodrigmatrix.domain.repository.PackageTrackerAnalytics

class PackageTrackerAnalyticsImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : PackageTrackerAnalytics {

    override fun sendEvent(name: String, params: Map<String, String>?) {
        firebaseAnalytics.logEvent(name, params?.toBundle())
    }

    private fun Map<String, Any?>?.toBundle(): Bundle? = this?.toList()?.toTypedArray()
        ?.let { bundleOf(*it) }
}