package com.sustenanceandroid.module

import android.util.Log
import java.time.Duration
import java.time.ZonedDateTime

object Formatter {

    private const val TAG = "APP ERROR"

    fun formatTimestamp(timestamp: String): String {
        return try {
            val formatedTimestamp = ZonedDateTime.parse(timestamp)
            val timestampNow = ZonedDateTime.now()
            val duration = Duration.between(formatedTimestamp, timestampNow)
            when {
                duration.seconds < 60 -> "just now"
                duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
                duration.toHours() < 24 -> "${duration.toHours()} hours ago"
                duration.toDays() < 30 -> "${duration.toDays()} days ago"
                duration.toDays() < 365 -> "${duration.toDays() / 30} months ago"
                else -> "${duration.toDays() / 365} years ago"
            }
        } catch (e: Exception) {
            e.localizedMessage?.let { message -> Log.d(TAG, message) }
            ""
        }
    }
}
