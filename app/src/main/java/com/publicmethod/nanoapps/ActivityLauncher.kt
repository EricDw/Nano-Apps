package com.publicmethod.nanoapps

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

object ActivityLauncher {

    private const val URL_BASE = "https://nanoapps.publicmethod.com"
    private const val URL_EDITOR = "$URL_BASE/editor"
    private const val URL_VIEWER = "$URL_BASE/viewer"

    fun launchEditor(
        activity: Activity,
        options: ActivityOptionsCompat? = null,
        intentModifier: Intent.() -> Unit = {}
    ) = baseIntent(URL_EDITOR, activity)
        .applyModifierThenStart(activity, options, intentModifier)

    fun launchViewer(
        activity: Activity,
        options: ActivityOptionsCompat? = null,
        intentModifier: Intent.() -> Unit = {}
    ) = baseIntent(URL_VIEWER, activity)
        .applyModifierThenStart(activity, options, intentModifier)

    private fun Intent.applyModifierThenStart(
        activity: Activity,
        options: ActivityOptionsCompat? = null,
        intentModifier: Intent.() -> Unit = {}
    ) = apply(intentModifier)
        .let { starter ->
            options?.run {
                ActivityCompat.startActivity(activity, starter, toBundle())
            } ?: activity.startActivity(starter)

        }

    private fun baseIntent(
        @Suppress("SameParameterValue") url: String,
        context: Context? = null
    ): Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {

        addCategory(Intent.CATEGORY_DEFAULT)
        addCategory(Intent.CATEGORY_BROWSABLE)
        context?.run {
            `package` = packageName
        }
    }

}