package com.example.frankito.hive.ui.view.Insects

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Direction
import com.example.frankito.hive.model.HexaCell
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.util.ColorizedDrawable
import com.example.frankito.hive.util.HexaHelper
import kotlinx.android.synthetic.main.view_hexa_element.view.*

class Grasshopper : HexaElement {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun setElementPlayerType(whichPlayer: WhichPlayer) {
        when (whichPlayer) {
            WhichPlayer.PLAYERONE -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.grasshopper, ContextCompat.getColor(context, R.color.black)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.lightground)
            }
            WhichPlayer.PLAYERTWO -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.grasshopper, ContextCompat.getColor(context, R.color.white)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.darkground)
            }
        }
    }

    override fun getDisableCellsByMoveLogic(availableCells: ArrayList<HexaCell>, elements: ArrayList<HexaElement>): ArrayList<HexaCell> {

        val reachableCells = ArrayList<HexaCell>()
        val disableCells = ArrayList<HexaCell>()
        val neighbourElements = ArrayList<HexaElement>()

        for (it in elements) {
            if (it.currentRow != null && it.currentCol != null) {
                if (HexaHelper.checkIfTwoElementsAreNeighbour(this, it)) {
                    neighbourElements.add(it)
                    val direction = HexaHelper.getDirectionOfNeighbourElement(this.currentRow!!, this.currentCol!!, it.currentRow!!, it.currentCol!!)
                    //Check if the current element plus the direction if contain or not an element
                    if (direction != null) {
                        val reachable = iterateDirectionCheck(availableCells, direction, it)
                        reachableCells.add(reachable)
                    }
                }
            }
        }

        for (it in availableCells) {
            if (!reachableCells.contains(it)) {
                disableCells.add(it)
            }
        }

        return disableCells
    }

    private fun iterateDirectionCheck(availableCells: ArrayList<HexaCell>, direction: Direction, neigbourElement: HexaElement): HexaCell {

        for (it in availableCells) {
            if (neigbourElement.currentRow!! + direction.row == it.row && neigbourElement.currentCol!! + direction.col == it.col) {
                return it
            }
        }

        var row: Int
        var col: Int

        if (direction.row > 0) {
            row = direction.row + 1
        } else if (direction.row == 0) {
            row = 0
        } else {
            row = direction.row - 1
        }
        if (direction.col > 0) {
            col = direction.col + 1
        } else if (direction.col == 0) {
            col = 0
        } else {
            col = direction.col - 1
        }

        val directionPlus = Direction(row, col)

        return iterateDirectionCheck(availableCells, directionPlus, neigbourElement)
    }

    override fun getCellsToEnable(elements: ArrayList<HexaElement>): ArrayList<HexaCell> {
        return ArrayList()
    }

}