package com.publicmethod.nanoapps

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

object ActivityLaunchHelper {

    private const val URL_BASE = "https://nanoapps.publicmethod.com"
    private const val URL_EDITOR = "$URL_BASE/editor"

    fun launchEditor(
        activity: Activity,
        options: ActivityOptionsCompat? = null,
        intentModifier: Intent.() -> Unit = {}
    ) {
        val starter = editorSelectionIntent(activity)
            .apply(intentModifier)

        if (options == null) {
            activity.startActivity(starter)
        } else {
            ActivityCompat.startActivity(activity, starter, options.toBundle())
        }
    }

    private fun editorSelectionIntent(context: Context? = null) = baseIntent(URL_EDITOR, context)

    private fun baseIntent(
        @Suppress("SameParameterValue") url: String,
        context: Context? = null
    ): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addCategory(Intent.CATEGORY_DEFAULT)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        context?.run {
            intent.`package` = packageName

        }
        return intent
    }
}