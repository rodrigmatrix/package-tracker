package com.rodrigmatrix.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String?.toDate(pattern: String = "yyyy-MM-dd"): Date? {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        dateFormat.parse(this.orEmpty())
    } catch (e: Exception) {
        null
    }
}