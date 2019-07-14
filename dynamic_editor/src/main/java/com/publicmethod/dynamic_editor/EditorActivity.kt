package com.publicmethod.dynamic_editor

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

    private fun createContentView(editTextFactory: Context.() -> TextView = basicEditTextFactory): CoordinatorLayout =
        createCoordinatorLayout {
            id = R.id.coordinator_layout_main
        }.apply {

            createConstraintLayout {
                id = R.id.constraint_layout_main
            }.apply constraint@{
                editTextFactory().also(this@constraint::addView)
            }.also(::addView)
        }

    private val basicEditTextFactory: Context.() -> EditText = {
        createEditTextView(settings)
    }

    private val settings: EditText.() -> Unit = {
        setTextColor(Color.GREEN)
        setBackgroundColor(Color.BLACK)
        imeOptions = EditorInfo.IME_FLAG_NO_FULLSCREEN
        inputType = InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
        gravity = Gravity.TOP or Gravity.START
        isSingleLine = false
        layoutParams = constraintMatchParentParams
    }


}
