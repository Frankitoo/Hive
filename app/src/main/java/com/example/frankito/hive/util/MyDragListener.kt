package com.example.frankito.hive.util

import android.content.Context
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.frankito.hive.manager.GameManager
import com.example.frankito.hive.ui.activity.GameActivity
import com.example.frankito.hive.ui.view.HexaElement

class MyDragListener(val context: Context, val row: Int, val col: Int) : View.OnDragListener {

    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d("hahaha", "haha")
            }
            DragEvent.ACTION_DRAG_ENTERED -> {

            }
            DragEvent.ACTION_DRAG_EXITED -> {

            }
            DragEvent.ACTION_DROP -> {

                val view = event.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)
                val container = v as LinearLayout
                container.addView(view)
                view.visibility = View.VISIBLE
                view.setOnTouchListener(MyTouchListener(context))

                if (view is HexaElement) {
                    view.currentRow = row
                    view.currentCol = col
                }

                droppedAt(row, col)
                turnNextPlayer()
            }
            DragEvent.ACTION_DRAG_ENDED -> {

            }
            else -> {

            }
        }// do nothing
        return true
    }

    private fun droppedAt(row: Int, col: Int) {
        val parentActivity = context as GameActivity
        parentActivity.droppedAt(row, col)
    }

    private fun turnNextPlayer() {
        val parentActivity = context as GameActivity

        when (GameManager.currentPlayer) {
            HexaElement.WhichPlayer.PLAYERONE -> {
                parentActivity.setPlayerTwoTurn()
                GameManager.currentPlayer = HexaElement.WhichPlayer.PLAYERTWO
            }
            HexaElement.WhichPlayer.PLAYERTWO -> {
                parentActivity.setPlayerOneTurn()
                GameManager.currentPlayer = HexaElement.WhichPlayer.PLAYERONE
            }
        }
    }
}