package com.example.frankito.hive.ui.view

import android.content.Context
import android.util.AttributeSet
import com.example.frankito.hive.R
import com.example.frankito.hive.util.DisableDragListener
import com.example.frankito.hive.util.DisabledTouchListener
import com.example.frankito.hive.util.EnableChildDragListener
import com.example.frankito.hive.util.MyTouchListener


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

    var currentRow: Int? = null
    var currentCol: Int? = null

    override fun getLayoutRes() = R.layout.view_hexa_element

    abstract fun setElementPlayerType(whichPlayer: WhichPlayer)

    fun disableTouchListener() {
        setOnTouchListener(DisabledTouchListener())
    }

    fun enableTouchListener() {
        setOnTouchListener(MyTouchListener(context))
    }

    fun disableDragListener() {
        setOnDragListener(DisableDragListener(context))
    }

    fun enableDragListener() {
        setOnDragListener(EnableChildDragListener(context))
    }

}