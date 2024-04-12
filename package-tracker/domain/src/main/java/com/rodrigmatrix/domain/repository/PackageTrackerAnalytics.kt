package com.rodrigmatrix.domain.repository

import android.os.Bundle

interface PackageTrackerAnalytics {

    fun sendEvent(name: String, params: Map<String, String>? = null)
}