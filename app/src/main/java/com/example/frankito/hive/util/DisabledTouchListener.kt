package com.example.frankito.hive.util

import android.view.MotionEvent
import android.view.View

class DisabledTouchListener : View.OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            return false
    }
}