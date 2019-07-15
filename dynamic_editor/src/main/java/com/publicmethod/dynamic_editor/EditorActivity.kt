package com.publicmethod.dynamic_editor

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.publicmethod.nanoapps.ActivityLauncher
import com.publicmethod.nanoapps.EXTRA_KEY_CONTENT
import com.publicmethod.nanoapps.R
import com.publicmethod.viewfactories.factories.constraintMatchParentParams
import com.publicmethod.viewfactories.factories.createConstraintLayout
import com.publicmethod.viewfactories.factories.createCoordinatorLayout
import com.publicmethod.viewfactories.factories.createEditTextView

class EditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView())
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

    }

    private fun createContentView(

        editTextFactory: Context.() -> TextView =
            basicEditTextFactory

    ): CoordinatorLayout =
        createCoordinatorLayout {
            id = R.id.coordinator_layout_main
        }.apply {

            createConstraintLayout {
                id = R.id.constraint_layout_main
            }.apply constraint@{
                editTextFactory().apply textView@{

                    Button(this@EditorActivity).apply {
                        id = R.id.button_view
                        text = "PREVIEW"
                        gravity = Gravity.CENTER
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )

                        setOnClickListener {
                            ActivityLauncher.launchViewer(this@EditorActivity) {
                                putExtra(EXTRA_KEY_CONTENT, this@textView.text.toString())
                            }
                        }

                        this@constraint.addView(this)

                        ConstraintSet().apply {
                            clone(this@constraint)
                            connect(
                                id,
                                ConstraintSet.RIGHT,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.RIGHT
                            )
                            connect(
                                id,
                                ConstraintSet.BOTTOM,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.BOTTOM
                            )
                            connect(
                                id,
                                ConstraintSet.LEFT,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.LEFT
                            )
                        }.applyTo(this@constraint)

                    }
                    this@constraint.addView(this)
                    ConstraintSet().apply {
                        clone(this@constraint)
                        connect(
                            this@textView.id,
                            ConstraintSet.TOP,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP
                        )
                        connect(
                            this@textView.id,
                            ConstraintSet.RIGHT,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.RIGHT
                        )
                        connect(
                            this@textView.id,
                            ConstraintSet.BOTTOM,
                            R.id.button_view,
                            ConstraintSet.BOTTOM
                        )
                        connect(
                            this@textView.id,
                            ConstraintSet.LEFT,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.LEFT
                        )
                    }.applyTo(this@constraint)


                }
            }.also(::addView)
        }

    private val basicEditTextFactory: Context.() -> EditText = {
        createEditTextView(settings)
    }

    private val settings: EditText.() -> Unit = {
        id = R.id.edit_text_main
        imeOptions = EditorInfo.IME_FLAG_NO_FULLSCREEN
        inputType = InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
        gravity = Gravity.TOP or Gravity.START
        isSingleLine = false
        layoutParams = constraintMatchParentParams
        setTextColor(Color.GREEN)
        setBackgroundColor(Color.BLACK)
    }


}
