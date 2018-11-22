package com.example.frankito.hive.ui.activity

import android.content.ClipData
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnDragListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.frankito.hive.R
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        top_layout.setOnClickListener{
            Toast.makeText(this@TestActivity,"top",Toast.LENGTH_SHORT).show()
        }
        bottom_layout.setOnClickListener{
            Toast.makeText(this@TestActivity,"bottom",Toast.LENGTH_SHORT).show()
        }
        top_layout.setOnDragListener(MyDragListener())
        bottom_layout.setOnDragListener(MyDragListener())
        topper_layout.setOnDragListener(MyDragListener())
        item1.setOnTouchListener(MyTouchListener())
        item2.setOnTouchListener(MyTouchListener() )

    }

    inner class MyTouchListener : OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(
                        view)
                view.startDrag(data, shadowBuilder, view, 0)
                view.setVisibility(View.INVISIBLE)
                return true
            } else {
                return false
            }
        }
    }

    inner class MyDragListener : OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                }
                DragEvent.ACTION_DRAG_ENTERED -> v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.recyclerBackgroundGray))
                DragEvent.ACTION_DRAG_EXITED -> v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.colorAccent))
                DragEvent.ACTION_DROP -> {
                    // Dropped, reassign View to ViewGroup
                    val view = event.localState as View
                    val owner = view.parent as ViewGroup
                    owner.removeView(view)
                    val container = v as LinearLayout
                    container.addView(view)
                    view.visibility = View.VISIBLE
                }
                DragEvent.ACTION_DRAG_ENDED -> v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.colorAccent))
                else -> {
                }
            }// do nothing
            return true
        }
    }
}
