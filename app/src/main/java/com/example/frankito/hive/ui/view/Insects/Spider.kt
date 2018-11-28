package com.example.frankito.hive.ui.view.Insects

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.example.frankito.hive.R
import com.example.frankito.hive.model.HexaCell
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.util.ColorizedDrawable
import com.example.frankito.hive.util.HexaHelper
import kotlinx.android.synthetic.main.view_hexa_element.view.*

class Spider : HexaElement {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private var markedCells: ArrayList<HexaCell>

    init {
        markedCells = ArrayList()
    }

    override fun setElementPlayerType(whichPlayer: WhichPlayer) {
        when (whichPlayer) {
            WhichPlayer.PLAYERONE -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.spider, ContextCompat.getColor(context, R.color.black)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.lightground)
            }
            WhichPlayer.PLAYERTWO -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.spider, ContextCompat.getColor(context, R.color.white)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.darkground)
            }
        }
    }

    override fun getDisableCellsByMoveLogic(availableCells: ArrayList<HexaCell>, elements: ArrayList<HexaElement>): ArrayList<HexaCell> {

        val reachableCells = ArrayList<HexaCell>()
        val disableCells = ArrayList<HexaCell>()

        for (it in availableCells) {

            //Check if has neighbour available
            //IF has mark as FirstMoveCell

            if (this.currentRow != null && this.currentCol != null) {

                if (HexaHelper.checkIfCoordinatesAreNeightbour(it.row, it.col, this.currentRow!!, this.currentCol!!)) {
                    if (!HexaHelper.checkCellContainsElement(it)) {
                        val markedFirstMoveCell = it

                        //From FirstMoveCell check that other available cells are in neighbour
                        //IF has mark as SecondMoveCell

                        for (availableCell in availableCells) {
                            if (HexaHelper.checkIfTwoCellsAreNeighbour(markedFirstMoveCell, availableCell)) {
                                if (!HexaHelper.checkCellContainsElement(it)) {

                                    val markedSecondMoveCell = availableCell
                                    //From SecondMoveCell check that other available cells are in neighbour that's not the FirstMoveCell
                                    //IF has mark add to reachable cells
                                    for (availableCellFromSecond in availableCells) {
                                        if (HexaHelper.checkIfTwoCellsAreNeighbour(markedSecondMoveCell, availableCellFromSecond)) {
                                            if (!HexaHelper.checkCellContainsElement(it)) {
                                                if (markedFirstMoveCell != availableCellFromSecond) {
                                                    reachableCells.add(availableCellFromSecond)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
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

}