package com.publicmethod.nanoapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.publicmethod.viewfactories.factories.createConstraintLayout
import com.publicmethod.viewfactories.factories.createFloatingActionButton
import com.publicmethod.viewfactories.theme.constrainFAB
import com.publicmethod.viewfactories.theme.themeWithConstraintMargins

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createConstraintLayout {
            createFloatingActionButton {
                themeWithConstraintMargins()
                setOnClickListener {
                    ActivityLaunchHelper.launchEditor(
                        this@MainActivity
                    ) {
                        putExtra(EXTRA_KEY_CONTENT, "[U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F4BB] (PERSONAL COMPUTER)")
                    }
                }

                constrainFAB(this)
            }.also(::addView)
        }.also(::setContentView)

    }
}
