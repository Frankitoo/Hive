package com.example.frankito.hive.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

abstract class CustomView : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributes: AttributeSet?) : super(context, attributes) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(getLayoutRes(), this, true)

        initUI()
    }

    protected abstract fun getLayoutRes(): Int

    protected open fun initUI() {
        //override if needed
    }

}