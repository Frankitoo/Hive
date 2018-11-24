package com.example.frankito.hive.util

import android.content.Context
import android.view.DragEvent
import android.view.View

class DisableDragListener(val context : Context) : View.OnDragListener {

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