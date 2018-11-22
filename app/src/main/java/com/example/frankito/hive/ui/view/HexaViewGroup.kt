package com.example.frankito.hive.ui.view

import android.content.ClipData
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import com.example.frankito.hive.R
import com.example.frankito.hive.util.ColorizedDrawable


class HexaViewGroup : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val GAME_TYPE_HEX = "hex"
    private val GAME_TYPE_Y = "yType"

    private var mSize = 10
    private var mGameType = GAME_TYPE_HEX

    private var mCenterHorizontal = 1
    private var mCenterVertical = 1

    private var mSizeInvalidated = true

    private val INVALID_POINTER_ID = 1
    private var mActivePointerId = INVALID_POINTER_ID

    private var mScaleFactor = 1f
    private var mScaleDetector: ScaleGestureDetector? = null
    private val mScaleMatrix = Matrix()
    private val mScaleMatrixInverse = Matrix()

    private var mPosX: Float = 0.toFloat()
    private var mPosY: Float = 0.toFloat()
    private val mTranslateMatrix = Matrix()
    private val mTranslateMatrixInverse = Matrix()

    private var mLastTouchX: Float = 0.toFloat()
    private var mLastTouchY: Float = 0.toFloat()

    private var mFocusY: Float = 0.toFloat()

    private var mFocusX: Float = 0.toFloat()

    private var mDispatchTouchEventWorkingArray = FloatArray(2)
    private var mOnTouchEventWorkingArray = FloatArray(2)

    private lateinit var mCanvas: Canvas


    init {
        mTranslateMatrix.setTranslate(0F, 0F)
        mScaleMatrix.setScale(1F, 1F)
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate(mPosX, mPosY)
        canvas.scale(mScaleFactor, mScaleFactor, mFocusX, mFocusY)
        super.dispatchDraw(canvas)
        canvas.restore()

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        mDispatchTouchEventWorkingArray[0] = ev.x
        mDispatchTouchEventWorkingArray[1] = ev.y
        mDispatchTouchEventWorkingArray = screenPointsToScaledPoints(mDispatchTouchEventWorkingArray)
        ev.setLocation(mDispatchTouchEventWorkingArray[0],
                mDispatchTouchEventWorkingArray[1])
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Although the docs say that you shouldn't override this, I decided to do
     * so because it offers me an easy way to change the invalidated area to my
     * likening.
     */


    private fun scaledPointsToScreenPoints(a: FloatArray): FloatArray {
        mScaleMatrix.mapPoints(a)
        mTranslateMatrix.mapPoints(a)
        return a
    }

    private fun screenPointsToScaledPoints(a: FloatArray): FloatArray {
        mTranslateMatrixInverse.mapPoints(a)
        mScaleMatrixInverse.mapPoints(a)
        return a
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mOnTouchEventWorkingArray[0] = ev.x
        mOnTouchEventWorkingArray[1] = ev.y

        mOnTouchEventWorkingArray = scaledPointsToScreenPoints(mOnTouchEventWorkingArray)

        ev.setLocation(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1])
        mScaleDetector?.onTouchEvent(ev)

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

                val dx = x - mLastTouchX
                val dy = y - mLastTouchY

                mPosX += dx
                mPosY += dy
                mTranslateMatrix.preTranslate(dx, dy)
                mTranslateMatrix.invert(mTranslateMatrixInverse)

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
        return true
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d("onBoard", "board.onlayout called with size $mSize l: $l r: $r t: $t b: $b")

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
        when (mCenterHorizontal) {
        //the left side of the grid should be at non-negative coordinates.
            1 -> {
                x_offset_row += Math.max(0f, (r.toFloat() - l.toFloat() - totalWidth) / 2)
            }
            2 -> {
                x_offset_row += Math.max(0f, r.toFloat() - l.toFloat() - totalWidth)
            }
            0 -> {
            }
            else -> {
            }
        }

        //calculate the y_offset for vertical centering.
        var y_offset = 0f
        when (mCenterVertical) {
            1 -> {
                y_offset = Math.max(0f, (b.toFloat() - t.toFloat() - totalHeight) / 2)
            }
            2 -> {
                y_offset = Math.max(0f, b.toFloat() - t.toFloat() - totalHeight)
            }
        }

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
            Log.d("board", "Drawing row $row with $rowLength cells.")
            for (col in 0 until rowLength) {
                val v: LinearLayout
                if (cell < childCount) {
                    v = getChildAt(cell) as LinearLayout
                } else {
                    v = LinearLayout(super.getContext())
                    v.setLayoutParams(layoutParams)

                    v.setOnTouchListener(NoTouchListener())

                    addViewInLayout(v, cell, v.layoutParams, true)
                }

                v.background = ColorizedDrawable.getColorizedDrawable(context, R.drawable.darkground, ContextCompat.getColor(context, R.color.mapBackground))
                v.scaleY = 0.89F
                v.scaleX = 0.8F

                v.setOnDragListener(MyDragListener())

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

    inner class MyTouchListener : OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(
                        view)
                view.startDrag(data, shadowBuilder, view, 0)
                view.setVisibility(View.INVISIBLE)
                return true
            } else {
                return false
            }
        }
    }

    inner class NoTouchListener : OnTouchListener {
        override fun onTouch(view: View, ev: MotionEvent): Boolean {
            mOnTouchEventWorkingArray[0] = ev.x
            mOnTouchEventWorkingArray[1] = ev.y

            mOnTouchEventWorkingArray = scaledPointsToScreenPoints(mOnTouchEventWorkingArray)

            ev.setLocation(mOnTouchEventWorkingArray[0], mOnTouchEventWorkingArray[1])
            mScaleDetector?.onTouchEvent(ev)

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

                    val dx = x - mLastTouchX
                    val dy = y - mLastTouchY
                    Log.d("nezzukmeg dx: ", dx.toString() + "   " + dy.toString())
                    mPosX += dx
                    mPosY += dy
                    mTranslateMatrix.preTranslate(dx, dy)
                    mTranslateMatrix.invert(mTranslateMatrixInverse)
                    Log.d("nezzukmeg mPos: ", mPosX.toString() + "   " + mPosY.toString())
                    mLastTouchX = x
                    mLastTouchY = y
                    Log.d("nezzukmeg LastTouch: ", mLastTouchX.toString() + "   " + mLastTouchY.toString())
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
            return true
        }
    }

    inner class MyDragListener : OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    val container = v as LinearLayout
                    container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                }
                DragEvent.ACTION_DROP -> {
                    // Dropped, reassign View to ViewGroup
                    if (v is LinearLayout) {
                        Toast.makeText(context, "row", Toast.LENGTH_SHORT).show()
                    }
                    val view = event.localState as View
                    val owner = view.parent as ViewGroup
                    owner.removeView(view)
                    val container = v as LinearLayout
                    container.addView(view)
                    view.visibility = View.VISIBLE
                    view.setOnTouchListener(MyTouchListener())
                }

                DragEvent.ACTION_DRAG_ENDED -> {

                }
                else -> {
                }
            }// do nothing
            return true
        }
    }
}