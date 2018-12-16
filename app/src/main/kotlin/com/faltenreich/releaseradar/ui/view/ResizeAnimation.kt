package com.faltenreich.releaseradar.ui.view

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class ResizeAnimation(internal var view: View, internal val targetHeight: Int, internal var startHeight: Int) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newHeight = (startHeight + targetHeight * interpolatedTime).toInt()
        //to support decent animation, change new heigt as Nico S. recommended in comments
        //int newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
        view.getLayoutParams().height = newHeight
        view.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}