package com.publicmethod.nanoapps

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.*
import com.publicmethod.viewfactories.dp
import com.publicmethod.viewfactories.factories.*
import com.publicmethod.viewfactories.theme.applyConnections
import com.publicmethod.viewfactories.theme.constrainToTopLeftAndRighOfParent
import io.noties.markwon.Markwon
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val markwon = Markwon.create(this@MainActivity)

        createConstraintLayout constraint@{

            setPadding(dp(8), dp(8), dp(8), dp(8))

            setBackgroundColor(Color.BLACK)

            createTextView {

                id = R.id.text_view_title

                setTextColor(Color.GREEN)

                textAlignment = TextView.TEXT_ALIGNMENT_CENTER

                layoutParams = viewGroupMatchParentWidthParams

                with(this, ::addView)

                with(this, ::constrainToTopLeftAndRighOfParent)

            }.also { markwon.setMarkdown(it, "# Nano Apps") }

            createTextView {

                id = R.id.text_view_options

                setTextColor(Color.GREEN)

                layoutParams = marginMatchParentWidthParams

                with(this, ::addView)

                applyConnections {
                    connect(id, LEFT, PARENT_ID, LEFT)
                    connect(id, RIGHT, PARENT_ID, RIGHT)
                    connect(id, TOP, R.id.text_view_title, BOTTOM)
                    connect(id, BOTTOM, R.id.edit_text_main, TOP)
                }

            }.also {
                markwon.setMarkdown(
                    it,
                    """
                        ### Accepts the following commands:
                        
                        1. Editor  
                    """.trimIndent()
                )
            }

            createEditTextView {

                id = R.id.edit_text_main

                hint = "INPUT COMMAND"

                isSingleLine = true

                layoutParams = marginMatchParentWidthParams

                setOnKeyListener { _, keyCode, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER
                    ) when (text.toString().trim().toLowerCase(Locale.getDefault())) {
                            "1" -> ActivityLauncher.launchEditor(this@MainActivity)
                            "1." -> ActivityLauncher.launchEditor(this@MainActivity)
                            "editor" -> ActivityLauncher.launchEditor(this@MainActivity)
                        }

                    true
                }

                setBackgroundColor(Color.BLACK)
                setHintTextColor(Color.GREEN)
                setTextColor(Color.GREEN)

                with(this, ::addView)

                applyConnections {
                    connect(id, BOTTOM, PARENT_ID, BOTTOM)
                    connect(id, RIGHT, PARENT_ID, RIGHT)
                    connect(id, LEFT, PARENT_ID, LEFT)
                }

            }

        }.also(::setContentView)

    }
}
