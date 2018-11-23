package com.example.frankito.hive.ui.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.Toast
import com.example.frankito.hive.R
import com.example.frankito.hive.util.ColorizedDrawable
import com.example.frankito.hive.util.Constants
import com.example.frankito.hive.util.DisabledTouchListener
import com.example.frankito.hive.util.MyTouchListener
import kotlinx.android.synthetic.main.view_hexa_element.view.*


abstract class HexaElement : CustomView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    enum class WhichPlayer {
        PLAYERONE, PLAYERTWO
    }

    var whichPlayer: WhichPlayer? = null
        set(value) {
            field = value
            value?.let {
                setElementPlayerType(value)
            }
        }

    override fun getLayoutRes() = R.layout.view_hexa_element

    abstract fun setElementPlayerType(whichPlayer: WhichPlayer)

    fun disableTouchListener() {
        setOnTouchListener(DisabledTouchListener())
    }

    fun enableTouchListener() {
        setOnTouchListener(MyTouchListener())
    }

}