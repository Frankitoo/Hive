package com.example.frankito.hive.manager

import android.content.Context
import android.support.v4.content.ContextCompat
import com.example.frankito.hive.R
import com.example.frankito.hive.model.HexaCell
import com.example.frankito.hive.ui.activity.GameActivity
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.ui.view.HexaViewGroup
import com.example.frankito.hive.ui.view.Insects.*
import com.example.frankito.hive.util.ColorizedDrawable
import com.example.frankito.hive.util.MyDragListener

class GameManager(val context: Context, val hexaViewGroup: HexaViewGroup) {

    companion object {
        val NUMBER_OF_ANTS = 3
        val NUMBER_OF_SPIDERS = 2
        val NUMBER_OF_QUEENS = 1
        val NUMBER_OF_STAGBEETLES = 2
        val NUMBER_OF_GRASSHOPPERS = 3

        var currentPlayer: HexaElement.WhichPlayer = HexaElement.WhichPlayer.PLAYERONE

        var firstMove = true
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
        playerOneViews = ArrayList()
        playerTwoViews = ArrayList()

        setPlayerOneTurn()

        insertViewsToLayouts()
    }

    fun setPlayerOneTurn() {
        enablePlayerOneViews()
        disablePlayerTwoViews()
    }

    fun setPlayerTwoTurn() {
        enablePlayerTwoViews()
        disablePlayerOneViews()
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

    private fun addPlayersViews(hexaElement: HexaElement) {
        when (hexaElement.whichPlayer) {
            HexaElement.WhichPlayer.PLAYERONE -> {
                playerOneViews?.add(hexaElement)
            }
            HexaElement.WhichPlayer.PLAYERTWO -> {
                playerTwoViews?.add(hexaElement)
            }
        }
    }

    private fun insertInsectToLayout(hexaElement: HexaElement) {
        val activity = context as GameActivity
        hexaElement.enableTouchListener()
        activity.insertInsectToLayout(hexaElement)
    }

    fun droppedAt(row: Int, col: Int) {

        if (firstMove){
            hexaViewGroup.disableDragListenersForViews()
            firstMove = false
        }

        val tempHexaCellOwn = getArrayElementByRowCol(row - 1, col + 1)

        val tempHexaCell1 = getArrayElementByRowCol(row - 1, col + 1)
        val tempHexaCell2 = getArrayElementByRowCol(row, col + 1)
        val tempHexaCell3 = getArrayElementByRowCol(row + 1, col)
        val tempHexaCell4 = getArrayElementByRowCol(row + 1, col + -1)
        val tempHexaCell5 = getArrayElementByRowCol(row, col - 1)
        val tempHexaCell6 = getArrayElementByRowCol(row - 1, col)

        val cellsArray = ArrayList<HexaCell>()
        cellsArray.add(tempHexaCellOwn)
        cellsArray.add(tempHexaCell1)
        cellsArray.add(tempHexaCell2)
        cellsArray.add(tempHexaCell3)
        cellsArray.add(tempHexaCell4)
        cellsArray.add(tempHexaCell5)
        cellsArray.add(tempHexaCell6)

        for (it in cellsArray) {
            it.layout.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.colorPrimary))
            it.layout.setOnDragListener(MyDragListener(context, it.row, it.col))
        }

    }

    private fun getArrayElementByRowCol(row: Int, col: Int): HexaCell {
        val index = row * HexaViewGroup.mSize + col
        return hexaViewGroup.viewArray[index]
    }

}