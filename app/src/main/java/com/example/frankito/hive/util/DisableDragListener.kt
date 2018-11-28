package com.example.frankito.hive.util

import android.content.Context
import android.view.DragEvent
import android.view.View
import com.example.frankito.hive.ui.activity.GameActivity
import com.example.frankito.hive.ui.view.HexaElement

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
                view.setOnTouchListener(MyTouchListener(context))

                if(view is HexaElement){
                    if(view.currentRow != null && view.currentCol != null){
                        val parent = context as GameActivity
                        parent.restoreStartDragViews()
                    }
                }
            }
            DragEvent.ACTION_DRAG_ENDED -> {

            }
            else -> {

            }
        }// do nothing
        return true
    }
}