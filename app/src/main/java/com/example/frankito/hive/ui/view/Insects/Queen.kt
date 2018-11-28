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

class Queen : HexaElement {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun setElementPlayerType(whichPlayer: WhichPlayer) {
        when (whichPlayer) {
            WhichPlayer.PLAYERONE -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.queen, ContextCompat.getColor(context, R.color.black)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.lightground)
            }
            WhichPlayer.PLAYERTWO -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.queen, ContextCompat.getColor(context, R.color.white)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.darkground)
            }
        }
    }

    override fun getDisableCellsByMoveLogic(availableCells: ArrayList<HexaCell>, elements: ArrayList<HexaElement>): ArrayList<HexaCell> {

        val cellsToDisable = ArrayList<HexaCell>()

        for (it in availableCells) {
            if (this.currentRow != null && this.currentCol != null) {
                if (!HexaHelper.checkIfCoordinatesAreNeightbour(it.row, it.col, this.currentRow!!, this.currentCol!!)) {
                    cellsToDisable.add(it)
                }
            }
        }

        return cellsToDisable
    }

}