package com.publicmethod.dynamic_markdown_viewer

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.publicmethod.nanoapps.EXTRA_KEY_CONTENT
import com.publicmethod.viewfactories.factories.createTextView
import com.publicmethod.viewfactories.factories.viewGroupMatchParentParams
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tasklist.TaskListPlugin

class ViewerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView())
    }

    private fun createContentView(): View =
        createTextView(settings).apply {
            intent?.getStringExtra(EXTRA_KEY_CONTENT)?.run {

                Markwon.builder(this@ViewerActivity).apply {
                    usePlugin(TaskListPlugin.create(this@ViewerActivity))
                }.build().setMarkdown(
                    this@apply, this
                )
            }
        }

//
//    private fun createContentView(editTextFactory: Context.() -> TextView = basicEditTextFactory): CoordinatorLayout =
//        createCoordinatorLayout {
//            id = R.id.coordinator_layout_main
//        }.apply {
//
//            createConstraintLayout {
//                id = R.id.constraint_layout_main
//            }.apply constraint@{
//                editTextFactory().also(this@constraint::addView)
//            }.also(::addView)
//        }
//
//    private val basicEditTextFactory: Context.() -> EditText = {
//        createEditTextView(settings)
//    }

    private val settings: TextView.() -> Unit = {
        setTextColor(Color.GREEN)
        setBackgroundColor(Color.BLACK)
        gravity = Gravity.TOP or Gravity.START
        layoutParams = viewGroupMatchParentParams
    }

}