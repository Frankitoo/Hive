package com.example.frankito.hive.util

import android.content.ClipData
import android.content.Context
import android.view.MotionEvent
import android.view.View
import com.example.frankito.hive.ui.activity.GameActivity
import com.example.frankito.hive.ui.view.HexaElement

class MyTouchListener(val context: Context) : View.OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        if (motionEvent.action == MotionEvent.ACTION_DOWN) {

            if (view is HexaElement) {
                val parent = context as GameActivity
                if (view.currentCol != null && view.currentRow != null) {
                    parent.dragStartedAt(view)
                }
            }

            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(view)
            view.startDrag(data, shadowBuilder, view, 0)
            view.visibility = View.VISIBLE

            return true

        } else {
            return false
        }
    }
}