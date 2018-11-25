package com.example.frankito.hive.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import com.example.frankito.hive.R
import com.example.frankito.hive.model.HexaCell
import com.example.frankito.hive.util.ColorizedDrawable
import com.example.frankito.hive.util.DisableDragListener
import com.example.frankito.hive.util.MyDragListener


class HexaViewGroup : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    companion object {
        var mSize = 25
    }

    private val GAME_TYPE_HEX = "hex"
    private val GAME_TYPE_Y = "yType"

    private var mGameType = GAME_TYPE_HEX

    private var mSizeInvalidated = true

    private val INVALID_POINTER_ID = 1
    private var mActivePointerId = INVALID_POINTER_ID

    private var mPosX: Float = 0F
    private var mPosY: Float = 0F

    private var mLastTouchX: Float = 0F
    private var mLastTouchY: Float = 0F

    var viewArray: ArrayList<HexaCell>

    init {
        viewArray = ArrayList()
    }

    private fun mTouchEvent(ev: MotionEvent) {

        val action = ev.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val x = ev.x
                val y = ev.y

                mLastTouchX = x
                mLastTouchY = y

                // Save the ID of this pointer
                mActivePointerId = ev.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                // Find the index of the active pointer and fetch its position
                val pointerIndex = ev.findPointerIndex(mActivePointerId)

                val x = ev.getX(pointerIndex)
                val y = ev.getY(pointerIndex)

                val dx = (x - mLastTouchX) / 3
                val dy = (y - mLastTouchY) / 3

                mPosX += dx
                mPosY += dy

                mLastTouchX = x
                mLastTouchY = y

                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                mActivePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_CANCEL -> {
                mActivePointerId = INVALID_POINTER_ID
            }

            MotionEvent.ACTION_POINTER_UP -> {
                // Extract the index of the pointer that left the touch sensor
                val pointerIndex = action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mLastTouchX = ev.getX(newPointerIndex)
                    mLastTouchY = ev.getY(newPointerIndex)
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                }
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        //canvas.translate(mPosX, mPosY)
        for (it in viewArray) {
            it.layout.translationX = mPosX
            it.layout.translationY = mPosY
        }

        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mTouchEvent(ev)
        return true
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        //If the dimensions of the board haven't changed, a redraw isn't necessary. Just update the images of the views instead by calling invalidate().
        if (!changed && !mSizeInvalidated) {
            invalidate()
            return
        }

        val childCount = childCount

        //Calculate some useful parameters.
        val radius = resources.getDimension(R.dimen.radius)
        val verticalMargin = -radius / 4
        val horizontalMargin = (Math.sqrt(3.0).toFloat() / 2 - 1) * radius
        val height = 2 * radius
        val effectiveHeight = height + 2 * verticalMargin
        val effectiveWidth = height + 2 * horizontalMargin

        val totalHeight = radius * (3 * mSize + 1) / 2
        val totalWidth: Float

        when (mGameType) {
            GAME_TYPE_HEX -> totalWidth = (mSize.toFloat() * 3 - 1) / 2 * Math.sqrt(3.0).toFloat() * radius
            GAME_TYPE_Y -> totalWidth = mSize * Math.sqrt(3.0).toFloat() * radius
            else -> totalWidth = mSize * Math.sqrt(3.0).toFloat() * radius
        }

        val layoutParams = ViewGroup.LayoutParams(height.toInt(), height.toInt())

        //Code to calculate the offsets for horizontal and vertical centering (this is an option in the .xml file)
        //The GAME_TYPE_HEX creates a tilted rectangular board and GAME_TYPE_Y creates a triangular board.
        var x_offset_row: Float

        when (mGameType) {
            GAME_TYPE_Y -> x_offset_row = (mSize - 1) * effectiveWidth / 2 + horizontalMargin
            GAME_TYPE_HEX -> x_offset_row = 0f
            else -> x_offset_row = 0f
        }

        x_offset_row += Math.max(0f, (r.toFloat() - l.toFloat() - totalWidth) / 2)

        //calculate the y_offset for vertical centering.
        var y_offset = Math.max(0f, (b.toFloat() - t.toFloat() - totalHeight) / 2)

        var cell = 0

        for (row in 0 until mSize) {

            var x_offset = x_offset_row
            val rowLength: Int

            //The row length depends on the board-type we want to draw.
            when (mGameType) {
                GAME_TYPE_HEX -> rowLength = mSize
                GAME_TYPE_Y -> rowLength = row + 1
                else -> rowLength = row + 1
            }

            for (col in 0 until rowLength) {
                val v: LinearLayout
                if (cell < childCount) {
                    v = getChildAt(cell) as LinearLayout
                } else {
                    v = LinearLayout(super.getContext())
                    v.setLayoutParams(layoutParams)

                    addViewInLayout(v, cell, v.layoutParams, true)
                }

                v.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.mapBackground))
                v.scaleY = 0.89F
                v.scaleX = 0.78F

                v.setOnTouchListener(NoTouchListener())

                viewArray.add(HexaCell(row, col, v))

                v.setTag(cell)

                //Set the bounds of the image, which will automatically be cropped in the available space.
                v.layout(x_offset.toInt(), y_offset.toInt(), (x_offset + height).toInt(), (y_offset + height).toInt())

                x_offset += effectiveWidth
                ++cell
            }

            y_offset += effectiveHeight

            //The offset of the next row, relative to this one, again depends on the game type.
            when (mGameType) {
                GAME_TYPE_Y -> x_offset_row -= effectiveWidth / 2
                GAME_TYPE_HEX -> x_offset_row += effectiveWidth / 2
            }

        }

        setDragListenersForViews()

        mPosX = -1800F
        mPosY = -1000F

        invalidate()

        //We updated all views, so it is not invalidated anymore.
        mSizeInvalidated = false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y

        setMeasuredDimension(width, height)

    }

    private fun setDragListenersForViews() {
        for (it in viewArray) {
            it.layout.setOnDragListener(MyDragListener(context, it.row, it.col))
            //Log.d("row - col", it.row.toString() + " - " + it.col.toString())
        }
    }

    fun disableDragListenersForViews() {
        for (it in viewArray) {
            it.layout.setOnDragListener(DisableDragListener(context))
        }
    }

    inner class NoTouchListener : OnTouchListener {
        override fun onTouch(view: View, ev: MotionEvent): Boolean {
            mTouchEvent(ev)
            return true
        }
    }
}