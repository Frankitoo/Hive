package com.example.frankito.hive.ui.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import com.example.frankito.hive.R
import com.example.frankito.hive.util.ColorizedDrawable
import com.example.frankito.hive.util.Constants
import kotlinx.android.synthetic.main.view_hexa_element.view.*


class HexaElement : CustomView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var bugType: String? = null
        set(value) {
            field = value
            setElementBugImage(value)
        }

    var backGround: String? = null
        set(value) {
            field = value
            setElementBackground(value)
        }

    override fun getLayoutRes() = R.layout.view_hexa_element

    private fun setElementBugImage(bugType: String?) {
        when (bugType) {
            Constants.ANT -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.ant, ContextCompat.getColor(context, R.color.black)))
            }
            Constants.GRASSHOPPER -> {

            }
            Constants.QUEEN -> {
                view_hexa_element_image.setImageDrawable(ColorizedDrawable.getColorizedDrawable(context, R.drawable.ant, ContextCompat.getColor(context, R.color.black)))
            }
            Constants.SPIDER -> {

            }
            Constants.STAGBEETLE -> {

            }
            else -> {
                Toast.makeText(context, "wrong bug type", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setElementBackground(groundType: String?) {

        val radius = resources.getDimension(R.dimen.radius)
        val height = 2 * radius
        val layoutParams = LinearLayout.LayoutParams(height.toInt(), height.toInt())

        when (groundType) {
            Constants.LIGHTGROUND -> {
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.lightground)
            }
            Constants.DARKGROUND -> {
                view_hexa_element_layout.background = ContextCompat.getDrawable(context, R.drawable.darkground)
            }
            Constants.BROWNGROUND -> {

            }
            Constants.NEUTRALGROUND -> {

            }
            else -> {
                Toast.makeText(context, "wrong ground type", Toast.LENGTH_SHORT).show()
            }
        }
        //view_hexa_element_layout.scaleY = 0.89F
        //view_hexa_element_layout.scaleX = 0.8F
        view_hexa_element_layout.layoutParams = layoutParams
    }
}