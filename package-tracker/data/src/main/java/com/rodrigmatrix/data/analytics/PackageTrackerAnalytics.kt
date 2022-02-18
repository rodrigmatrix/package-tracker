package com.rodrigmatrix.data.analytics

import android.os.Bundle

interface PackageTrackerAnalytics {

    fun sendEvent(name: String, params: Bundle? = null)
}