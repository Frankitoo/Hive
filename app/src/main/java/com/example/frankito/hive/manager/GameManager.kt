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
import com.example.frankito.hive.util.DisableDragListener
import com.example.frankito.hive.util.HexaHelper
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
    var playerTwoViews: ArrayList<HexaElement>? = null

    var activeCells: ArrayList<HexaCell>? = null

    private var disabledCellsAtStartDrag: ArrayList<HexaCell>? = null

    private var elements: ArrayList<HexaElement>? = null

    private var markedElements: ArrayList<HexaElement>? = null
    private var usedElements: ArrayList<HexaElement>? = null

    private var essentialPoints: ArrayList<HexaElement>? = null

    fun initGame() {
        playerOneViews = ArrayList()
        playerTwoViews = ArrayList()
        disabledCellsAtStartDrag = ArrayList()
        activeCells = ArrayList()
        elements = ArrayList()
        essentialPoints = ArrayList()

        setPlayerOneTurn()
        GameManager.firstMove = true

        insertViewsToLayouts()
    }

    fun checkAllElementIfEssential() {
        essentialPoints = ArrayList()
        elements?.let {
            val currentElements = it.clone() as ArrayList<HexaElement>
            for (iter in it) {
                if (!checkIfEssentialPoint(iter, currentElements)) {
                    iter.enableTouchListener()
                } else {
                    essentialPoints!!.add(iter)
                    iter.disableTouchListener()
                }
            }
        }
    }

    fun checkIfEssentialPoint(element: HexaElement, elements: ArrayList<HexaElement>): Boolean {

        val elementsCount = elements.size

        if (elementsCount > 2) {

            val otherElements = elements.clone() as ArrayList<HexaElement>
            otherElements.remove(element)

            val otherElementsCount = elementsCount - 1

            val countBFS = countBFSSearch(otherElements)

            return (countBFS != otherElementsCount)

        } else {
            return false
        }
    }

    fun countBFSSearch(otherElements: ArrayList<HexaElement>): Int? {

        //INIT BFS Search

        markedElements = ArrayList()
        usedElements = ArrayList()

        val startElement = otherElements[0]

        markedElements?.add(startElement)

        //ITERATION LOOP

        BFSIteration(startElement, otherElements)

        val count = markedElements?.size

        return count
    }

    fun BFSIteration(element: HexaElement, otherElements: ArrayList<HexaElement>) {
        val neighbourElements = getNeighBourElements(element, otherElements)
        for (neighbour in neighbourElements) {
            if (!markedElements!!.contains(neighbour)) {
                markedElements?.add(neighbour)
                BFSIteration(neighbour, otherElements)
            }
        }
    }

    fun getAllPlayerElement() {
        elements = ArrayList()
        for (it in playerOneViews!!) {
            if (it.currentCol != null && it.currentRow != null) {
                elements!!.add(it)
            }
        }
        for (it in playerTwoViews!!) {
            if (it.currentCol != null && it.currentRow != null) {
                elements!!.add(it)
            }
        }
    }

    fun setPlayerOneTurn() {
        enablePlayerOneViews()
        disablePlayerTwoViews()
    }

    fun setPlayerTwoTurn() {
        enablePlayerTwoViews()
        disablePlayerOneViews()
    }

    fun restoreViews() {
        disabledCellsAtStartDrag?.let {
            for (iter in it) {
                iter.layout.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.colorPrimary))

                if (!checkCellContainsElement(iter)) {
                    iter.layout.setOnDragListener(MyDragListener(context, iter.row, iter.col))
                }
            }
        }
        disabledCellsAtStartDrag = ArrayList()
    }

    fun droppedAt(row: Int, col: Int) {

        if (firstMove) {
            hexaViewGroup.disableDragListenersForViews()
            firstMove = false
        }

        restoreViews()

        //Neighbour cells
        val cellsArray = getNeighBours(row, col)
        //Owner cell
        cellsArray.add(getArrayElementByRowCol(row, col))

        for (it in cellsArray) {
            it.layout.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.colorPrimary))

            if (!checkCellContainsElement(it)) {
                it.layout.setOnDragListener(MyDragListener(context, it.row, it.col))
            }

            if(!activeCells!!.contains(it)) {
                activeCells?.add(it)
            }
        }

        getArrayElementByRowCol(row, col).layout.setOnDragListener(DisableDragListener(context))

        getAllPlayerElement()
        checkAllActiveCells()
        checkAllElementIfEssential()

    }

    fun dragStartedAt(element: HexaElement) {

        val neighbours = getNeighBours(element.currentRow!!, element.currentCol!!)

        for (it in neighbours) {
            if (countNeighbours(it) < 2) {
                it.layout.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.mapBackground))
                it.layout.setOnDragListener(DisableDragListener(context))
                disabledCellsAtStartDrag?.add(it)
            }
        }

        val availableCells = getAvailableCells()
        val disabledCells = element.getDisableCellsByMoveLogic(availableCells)

        for (it in disabledCells) {
            it.layout.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.mapBackground))
            it.layout.setOnDragListener(DisableDragListener(context))
            disabledCellsAtStartDrag?.add(it)
        }

    }

    private fun getAvailableCells(): ArrayList<HexaCell> {
        val availableCells = ArrayList<HexaCell>()
        for (it in activeCells!!) {
            if (!checkCellContainsElement(it)) {
                if (!disabledCellsAtStartDrag!!.contains(it)) {
                    availableCells.add(it)
                }
            }
        }
        return availableCells
    }

    private fun checkAllActiveCells() {
        activeCells?.let {
            for (iter in it) {
                if (!checkIfHasNeighbour(iter)) {
                    iter.layout.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.mapBackground))
                    iter.layout.setOnDragListener(DisableDragListener(context))
                }
            }
        }
    }

    private fun getNeighBourElements(hexaElement: HexaElement, elements: ArrayList<HexaElement>): ArrayList<HexaElement> {

        val neighbourElements = ArrayList<HexaElement>()

        for (it in elements) {
            if (HexaHelper.checkIfTwoElementsAreNeighbour(hexaElement, it)) {
                neighbourElements.add(it)
            }
        }
        return neighbourElements
    }

    private fun getNeighBours(row: Int, col: Int): ArrayList<HexaCell> {

        val cellsArray = ArrayList<HexaCell>()

        //This is the owner cell
        //cellsArray.add(getArrayElementByRowCol(row, col))

        cellsArray.add(getArrayElementByRowCol(row - 1, col + 1))
        cellsArray.add(getArrayElementByRowCol(row, col + 1))
        cellsArray.add(getArrayElementByRowCol(row + 1, col))
        cellsArray.add(getArrayElementByRowCol(row + 1, col - 1))
        cellsArray.add(getArrayElementByRowCol(row, col - 1))
        cellsArray.add(getArrayElementByRowCol(row - 1, col))

        return cellsArray
    }

    private fun countNeighbours(hexaCell: HexaCell): Int {
        var count = 0
        val neighbours = getNeighBours(hexaCell.row, hexaCell.col)
        for (it in neighbours) {
            if (checkCellContainsElement(it)) {
                count++
            }
        }
        return count
    }

    private fun checkIfHasNeighbour(hexaCell: HexaCell): Boolean {
        val neighbours = getNeighBours(hexaCell.row, hexaCell.col)
        for (it in neighbours) {
            if (checkCellContainsElement(it)) {
                return true
            }
        }
        return false
    }

    private fun checkCellContainsElement(hexaCell: HexaCell): Boolean {
        return hexaCell.layout.childCount > 0
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
                if (!essentialPoints!!.contains(iteration)) {
                    iteration.enableTouchListener()
                }
            }
        }
    }

    private fun enablePlayerTwoViews() {
        playerTwoViews?.let {
            for (iteration in it) {
                if (!essentialPoints!!.contains(iteration)) {
                    iteration.enableTouchListener()
                }
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


    private fun getArrayElementByRowCol(row: Int, col: Int): HexaCell {
        val index = row * HexaViewGroup.mSize + col
        return hexaViewGroup.viewArray[index]
    }

}