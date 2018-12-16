package com.faltenreich.releaseradar.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.faltenreich.releaseradar.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class Toolbar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attributeSet, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_toolbar, this, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initLayout()
    }

    private fun initLayout() {
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = context.resources.getDimensionPixelSize(R.dimen.toolbar_height)
    }

    override fun setTitle(title: CharSequence?) {
        toolbar_title.text = title
    }
}