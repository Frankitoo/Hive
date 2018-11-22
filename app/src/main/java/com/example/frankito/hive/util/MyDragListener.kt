package com.example.frankito.hive.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.frankito.hive.R

class MyDragListener(val context : Context) : View.OnDragListener {



    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                //val container = v as LinearLayout
                //container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            DragEvent.ACTION_DRAG_EXITED -> {
            }
            DragEvent.ACTION_DROP -> {
                // Dropped, reassign View to ViewGroup
                if (v is LinearLayout) {
                    Toast.makeText(context, "row", Toast.LENGTH_SHORT).show()
                }
                val view = event.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)
                val container = v as LinearLayout
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