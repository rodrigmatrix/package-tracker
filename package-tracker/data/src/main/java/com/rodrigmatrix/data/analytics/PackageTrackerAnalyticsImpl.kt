package com.rodrigmatrix.data.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class PackageTrackerAnalyticsImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : PackageTrackerAnalytics {

    override fun sendEvent(name: String, params: Bundle?) {
        firebaseAnalytics.logEvent(name, params)
    }
}