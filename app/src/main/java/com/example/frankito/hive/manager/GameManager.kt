package com.example.frankito.hive.manager

import android.content.Context
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.ui.view.HexaViewGroup

class GameManager(val hexaViewGroup: HexaViewGroup, context: Context) {

    var playerOneViews: ArrayList<HexaElement>? = null
        set(value) {
            field = value

        }

    var playerTwoViews: ArrayList<HexaElement>? = null
        set(value) {
            field = value

        }

    fun disablePlayerOneViews() {
        playerOneViews?.let {
            for (iteration in it) {
                iteration.disableTouchListener()
            }
        }
    }

    fun disablePlayerTwoViews() {
        playerTwoViews?.let {
            for (iteration in it) {
                iteration.disableTouchListener()
            }
        }
    }

    fun enablePlayerOneViews() {
        playerOneViews?.let {
            for (iteration in it) {
                iteration.enableTouchListener()
            }
        }
    }

    fun enablePlayerTwoViews() {
        playerTwoViews?.let {
            for (iteration in it) {
                iteration.enableTouchListener()
            }
        }
    }

}