package com.publicmethod.nanoapps

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.*
import com.publicmethod.viewfactories.dp
import com.publicmethod.viewfactories.factories.*
import com.publicmethod.viewfactories.theme.applyConnections
import com.publicmethod.viewfactories.theme.applyFABConstraints
import com.publicmethod.viewfactories.theme.constrainToTopLeftAndRighOfParent
import io.noties.markwon.Markwon

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            }.also { Markwon.create(this@MainActivity).setMarkdown(it, "# Nano Apps") }

            createButton {

                text = "LAUNCH EDITOR"

                layoutParams = ViewGroup.MarginLayoutParams(layoutParams).apply {
                    setMargins(0, dp(40), 0, 0)
                }

                setBackgroundColor(Color.BLACK)
                setTextColor(Color.GREEN)

                setOnClickListener {
                    ActivityLauncher.launchEditor(this@MainActivity)
                }

                with(this, ::addView)

                applyConnections {
                    connect(id, TOP, R.id.text_view_title, BOTTOM)
                    connect(id, BOTTOM, PARENT_ID, BOTTOM)
                    connect(id, RIGHT, PARENT_ID, RIGHT)
                    connect(id, LEFT, PARENT_ID, LEFT)
                }

            }

//            createFloatingActionButton {
//
//                setOnClickListener {
//                    ActivityLauncher.launchEditor(
//                        this@MainActivity
//                    )
//                }
//
//                with(this, ::addView)
//
//                with(this, ::applyFABConstraints)
//            }
        }.also(::setContentView)

    }
}
