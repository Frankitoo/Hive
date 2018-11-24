package com.example.frankito.hive.util

import android.content.ClipData
import android.view.MotionEvent
import android.view.View

class MyTouchListener : View.OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(view)
            view.startDrag(data, shadowBuilder, view, 0)
            view.setVisibility(View.INVISIBLE)
            return true
        } else {
            return false
        }
    }
}