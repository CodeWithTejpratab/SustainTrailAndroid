package com.sustenanceandroid.module

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.time.Duration
import java.time.ZonedDateTime

object Utils {

    private const val TAG = "APP ERROR"

    fun formatTimestamp(timestamp: String): String {
        return try {
            val formatedTimestamp = ZonedDateTime.parse(timestamp)
            val timestampNow = ZonedDateTime.now()
            val duration = Duration.between(formatedTimestamp, timestampNow)
            when {
                duration.seconds < 60 -> "just now"
                duration.toMinutes() < 60 -> "${duration.toMinutes()} minute(s) ago"
                duration.toHours() < 24 -> "${duration.toHours()} hour(s) ago"
                duration.toDays() < 30 -> "${duration.toDays()} day(s) ago"
                duration.toDays() < 365 -> "${duration.toDays() / 30} month(s) ago"
                else -> "${duration.toDays() / 365} year(s) ago"
            }
        } catch (e: Exception) {
            e.localizedMessage?.let { message -> Log.d(TAG, message) }
            ""
        }
    }

    fun launchCustomChromeTab(context: Context, uri: Uri, @ColorInt toolbarColor: Int = Color.White.toArgb()) {
        val customTabBarColor = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(toolbarColor).build()
        val customTabsIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(customTabBarColor)
            .build()

        customTabsIntent.launchUrl(context, uri)
    }
}
