package com.example.frankito.hive.util

import android.content.Context
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class EnableChildDragListener(val context : Context) : View.OnDragListener {

    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {

            }
            DragEvent.ACTION_DRAG_ENTERED -> {

            }
            DragEvent.ACTION_DRAG_EXITED -> {

            }
            DragEvent.ACTION_DROP -> {
                val view = event.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)
                val container = v.parent as LinearLayout
                container.addView(view)
                view.visibility = View.VISIBLE
                view.setOnTouchListener(MyTouchListener())
            }
            DragEvent.ACTION_DRAG_ENDED -> {

            }
            else -> {

            }
        }// do nothing
        return true
    }
}