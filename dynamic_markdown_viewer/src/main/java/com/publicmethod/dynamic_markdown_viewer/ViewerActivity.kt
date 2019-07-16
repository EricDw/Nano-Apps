package com.publicmethod.dynamic_markdown_viewer

import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.publicmethod.nanoapps.EXTRA_KEY_CONTENT
import com.publicmethod.viewfactories.dp
import com.publicmethod.viewfactories.factories.createTextView
import com.publicmethod.viewfactories.factories.viewGroupMatchParentParams
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonSpansFactory
import io.noties.markwon.SpanFactory
import io.noties.markwon.ext.tasklist.TaskListItem
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.ext.tasklist.TaskListSpan

class ViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Markwon.builder(this@ViewerActivity)
            .usePlugin(TaskListPlugin.create(this@ViewerActivity))
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureSpansFactory(builder: MarkwonSpansFactory.Builder) {

                    // obtain original SpanFactory set by TaskListPlugin
                    val origin = builder.getFactory(TaskListItem::class.java)
                        ?: // or throw, as it's a bit weird state and we expect
                        // this factory to be present
                        return

                    builder.setFactory(
                        TaskListItem::class.java,
                        SpanFactory { configuration, props ->
                            // it's a bit non-secure behavior and we should validate
                            // the type of returned span first, but for the sake of brevity
                            // we skip this step
                            val span = origin.getSpans(configuration, props) as TaskListSpan?
                                ?: // or throw
                                return@SpanFactory null

                            // return an array of spans
                            arrayOf(span, object : ClickableSpan() {
                                override fun onClick(widget: View) {
                                    // toggle VISUAL state
                                    span.isDone = !span.isDone

                                    // do not forget to invalidate widget
                                    widget.invalidate()

                                    // execute your persistence logic
                                }

                                override fun updateDrawState(ds: TextPaint) {
                                    // no-op, so appearance is not changed (otherwise
                                    // task list item will look like a link)
                                }
                            })
                        })
                }
            }).build().run {
                setContentView(createContentView(this))
            }

    }

    private fun createContentView(markwon: Markwon): View =
        createTextView(settings).apply {
            intent?.getStringExtra(EXTRA_KEY_CONTENT)?.run {
                markwon.setMarkdown(
                    this@apply, this
                )
            }
        }

    private val settings: TextView.() -> Unit = {
        setTextColor(Color.GREEN)
        setBackgroundColor(Color.BLACK)
        gravity = Gravity.TOP or Gravity.START
        layoutParams = viewGroupMatchParentParams.apply {
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }
    }

}