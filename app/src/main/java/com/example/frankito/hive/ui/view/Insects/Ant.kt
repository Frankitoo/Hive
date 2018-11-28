package com.example.frankito.hive.ui.view.Insects

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.example.frankito.hive.R
import com.example.frankito.hive.model.HexaCell
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.util.ColorizedDrawable
import kotlinx.android.synthetic.main.view_hexa_element.view.*

class Ant : HexaElement {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun setElementPlayerType(whichPlayer: WhichPlayer) {
        view_hexa_element_image.scaleX = 0.85F
        view_hexa_element_image.scaleY = 0.85F

        when (whichPlayer) {
            WhichPlayer.PLAYERONE -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.ant, ContextCompat.getColor(context, R.color.black)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.lightground)
            }
            WhichPlayer.PLAYERTWO -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.ant, ContextCompat.getColor(context, R.color.white)))
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.darkground)
            }
        }
    }

    override fun getDisableCellsByMoveLogic(availableCells: ArrayList<HexaCell>) = ArrayList<HexaCell>()

}