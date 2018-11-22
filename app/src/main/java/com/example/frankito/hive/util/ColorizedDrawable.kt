package com.example.frankito.hive.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat

object ColorizedDrawable {

    fun getColorizedDrawable(context: Context, drawableRes: Int, color: Int): Drawable {
        val drawableNormal = ContextCompat.getDrawable(context, drawableRes)
        val drawableWrap = DrawableCompat.wrap(drawableNormal!!)
        drawableWrap.mutate()
        DrawableCompat.setTint(drawableWrap, color)
        return drawableWrap
    }
}