package com.example.frankito.hive.manager

import android.content.Context
import com.example.frankito.hive.ui.activity.GameActivity
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.ui.view.HexaViewGroup
import com.example.frankito.hive.ui.view.Insects.*
import kotlinx.android.synthetic.main.activity_game.*

class GameManager(val hexaViewGroup: HexaViewGroup, val context: Context) {

    companion object {
        val NUMBER_OF_ANTS = 3
        val NUMBER_OF_SPIDERS = 2
        val NUMBER_OF_QUEENS = 1
        val NUMBER_OF_STAGBEETLES = 2
        val NUMBER_OF_GRASSHOPPERS = 3
    }

    var playerOneViews: ArrayList<HexaElement>? = null
        set(value) {
            field = value

        }

    var playerTwoViews: ArrayList<HexaElement>? = null
        set(value) {
            field = value

        }

    fun initGame() {

        playerOneViews = ArrayList<HexaElement>()
        playerTwoViews = ArrayList<HexaElement>()

        insertViewsToLayouts()

    }

    private fun disablePlayerOneViews() {
        playerOneViews?.let {
            for (iteration in it) {
                iteration.disableTouchListener()
            }
        }
    }

    private fun disablePlayerTwoViews() {
        playerTwoViews?.let {
            for (iteration in it) {
                iteration.disableTouchListener()
            }
        }
    }

    private fun enablePlayerOneViews() {
        playerOneViews?.let {
            for (iteration in it) {
                iteration.enableTouchListener()
            }
        }
    }

    private fun enablePlayerTwoViews() {
        playerTwoViews?.let {
            for (iteration in it) {
                iteration.enableTouchListener()
            }
        }
    }

    private fun insertViewsToLayouts() {

        val players = HexaElement.WhichPlayer.values()

        for (itPlayer in players) {
            for (it in 1..NUMBER_OF_ANTS) {
                val element = Ant(context)
                element.whichPlayer = itPlayer
                addPlayersViews(element)
                insertInsectToLayout(element)
            }
            for (it in 1..NUMBER_OF_GRASSHOPPERS) {
                val element = Grasshopper(context)
                element.whichPlayer = itPlayer
                addPlayersViews(element)
                insertInsectToLayout(element)
            }
            for (it in 1..NUMBER_OF_SPIDERS) {
                val element = Spider(context)
                element.whichPlayer = itPlayer
                addPlayersViews(element)
                insertInsectToLayout(element)
            }
            for (it in 1..NUMBER_OF_STAGBEETLES) {
                val element = Stagbeetle(context)
                element.whichPlayer = itPlayer
                addPlayersViews(element)
                insertInsectToLayout(element)
            }
            for (it in 1..NUMBER_OF_QUEENS) {
                val element = Queen(context)
                element.whichPlayer = itPlayer
                addPlayersViews(element)
                insertInsectToLayout(element)
            }
        }

    }

    private fun addPlayersViews(hexaElement: HexaElement){
        when(hexaElement.whichPlayer){
            HexaElement.WhichPlayer.PLAYERONE -> { playerOneViews?.add(hexaElement) }
            HexaElement.WhichPlayer.PLAYERTWO -> {  playerTwoViews?.add(hexaElement) }
        }
    }

    private fun insertInsectToLayout(hexaElement: HexaElement) {
        val activity = context as GameActivity
        hexaElement.enableTouchListener()
        activity.insertInsectToLayout(hexaElement)
    }


}