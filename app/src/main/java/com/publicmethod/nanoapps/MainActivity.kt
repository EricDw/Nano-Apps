package com.publicmethod.nanoapps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import com.publicmethod.viewfactories.factories.createConstraintLayout
import com.publicmethod.viewfactories.factories.createFloatingActionButton
import com.publicmethod.viewfactories.theme.constrainFAB
import com.publicmethod.viewfactories.theme.themeWithConstraintMargins

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val constraintLayout = createConstraintLayout {
            id = R.id.constraint_layout_main
        }.apply {
            createFloatingActionButton {
                id = R.id.fab_main
                setImageDrawable(
                    getDrawable(
                        this@MainActivity,
                        R.drawable.ic_edit_white_24dp
                    )
                )
                setOnClickListener {
                    showBottomInput()
                }
                themeWithConstraintMargins()
            }.also {
                addView(it)
                constrainFAB(it)
            }
        }

        setContentView(constraintLayout)
    }

    private fun showBottomInput() =
        Toast.makeText(
            this,
            "Showing bottom modal",
            Toast.LENGTH_LONG
        ).show()
}
