package com.example.frankito.hive.ui.activity

import android.content.ClipData
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.LinearLayout
import com.example.frankito.hive.R
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.ui.view.HexaViewGroup
import com.example.frankito.hive.util.Constants
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    private lateinit var viewHolder: Viewholder

    inner class Viewholder {
        internal var hexaGrid = findViewById<HexaViewGroup>(R.id.hexa_grid)
        internal val firstPlayerLayoutQueen = findViewById<LinearLayout>(R.id.first_player_layout_queen)
        internal val firstPlayerLayoutAnt = findViewById<LinearLayout>(R.id.first_player_layout_ant)
        internal val firstPlayerLayoutStagbeetle = findViewById<LinearLayout>(R.id.first_player_layout_stagbeetle)
        internal val firstPlayerLayoutSpider = findViewById<LinearLayout>(R.id.first_player_layout_spider)
        internal val firstPlayerLayoutGrasshopper = findViewById<LinearLayout>(R.id.first_player_layout_grasshopper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewHolder = Viewholder()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val radius = resources.getDimension(R.dimen.radius)
        val height = 2 * radius
        val layoutParams = LinearLayout.LayoutParams(height.toInt(), height.toInt())

        layoutParams.setMargins(0,0,0,8)
        viewHolder.firstPlayerLayoutAnt.layoutParams = layoutParams
        viewHolder.firstPlayerLayoutGrasshopper.layoutParams = layoutParams
        viewHolder.firstPlayerLayoutSpider.layoutParams = layoutParams
        viewHolder.firstPlayerLayoutStagbeetle.layoutParams = layoutParams
        viewHolder.firstPlayerLayoutQueen.layoutParams = layoutParams

        viewHolder.firstPlayerLayoutAnt.setOnDragListener(MyDragListener())
        viewHolder.firstPlayerLayoutGrasshopper.setOnDragListener(MyDragListener())
        viewHolder.firstPlayerLayoutSpider.setOnDragListener(MyDragListener())
        viewHolder.firstPlayerLayoutStagbeetle.setOnDragListener(MyDragListener())
        viewHolder.firstPlayerLayoutQueen.setOnDragListener(MyDragListener())

        temp_hexa.bugType = Constants.ANT
        temp_hexa.backGround = Constants.DARKGROUND
        temp_hexa.setOnTouchListener(MyTouchListener())
        temp_hexa2.bugType = Constants.ANT
        temp_hexa2.backGround = Constants.LIGHTGROUND
        temp_hexa2.setOnTouchListener(MyTouchListener())

        val hexaElement = HexaElement(this@GameActivity)
        hexaElement.backGround = Constants.LIGHTGROUND
        hexaElement.bugType = Constants.QUEEN
        first_player_set_layout.addView(hexaElement)
    }

    inner class MyTouchListener : View.OnTouchListener {
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

    inner class MyDragListener : View.OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {

                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                }
                DragEvent.ACTION_DROP -> {
                    // Dropped, reassign View to ViewGroup
                    val view = event.localState as View
                    val owner = view.parent as ViewGroup
                    owner.removeView(view)
                    val container = v as LinearLayout
                    container.addView(view)
                    view.visibility = View.VISIBLE
                    view.setOnTouchListener(MyTouchListener())


                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    //v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.colorAccent))

                }
                else -> {
                }
            }// do nothing
            return true
        }
    }

}
