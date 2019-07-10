package com.publicmethod.nanoapps

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.ContextCompat.getDrawable
import com.google.android.material.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
import com.publicmethod.viewfactories.factories.createBottomAppBar
import com.publicmethod.viewfactories.factories.createConstraintLayout
import com.publicmethod.viewfactories.factories.createCoordinatorLayout
import com.publicmethod.viewfactories.factories.createFloatingActionButton
import com.publicmethod.viewfactories.theme.themeWithBottomAppBarMargins

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = createCoordinatorLayout {
            id = R.id.coordinator_layout_main
        }.apply {

            createConstraintLayout {
                id = R.id.constraint_layout_main
            }.also {
                addView(it)
            }

            createBottomAppBar {
                id = R.id.app_bar_main
                fabAlignmentMode = FAB_ALIGNMENT_MODE_CENTER
                backgroundTint = ColorStateList.valueOf(getColor(R.color.colorPrimaryDark))
                navigationIcon = getDrawable(this@MainActivity, R.drawable.ic_menu_white_24dp)
            }.also {
                addView(it)
                setSupportActionBar(it)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }

            createFloatingActionButton {
                id = R.id.fab_main
                setImageDrawable(
                    getDrawable(
                        this@MainActivity,
                        R.drawable.ic_edit_white_30dp
                    )
                )
                setOnClickListener {
                    showBottomInput()
                }
                themeWithBottomAppBarMargins(
                    R.id.app_bar_main,
                    Gravity.CENTER
                )
            }.also {
                addView(it)
            }
        }

        setContentView(rootLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        menuInflater.inflate(R.menu.menu_main, menu).run {
            true
        }

    private fun showBottomInput() =
        Toast.makeText(
            this,
            "Showing bottom modal",
            Toast.LENGTH_LONG
        ).show()
}
