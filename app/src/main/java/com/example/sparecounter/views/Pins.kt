package com.example.sparecounter.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.TableLayout

//class Pins @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0,
//    pins: MutableList<Int> = mutableListOf<Int>()
//): View(context, attrs, defStyleAttr) {
//    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//        style = Paint.Style.FILL
//        textAlign = Paint.Align.CENTER
//        textSize = 55.0f
//        typeface = Typeface.create( "", Typeface.BOLD)
//    }
//
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        paint.color = Color.BLUE
//        canvas!!.drawCircle((50).toFloat(), (50).toFloat(), (30).toFloat(), paint);
//    }
//
//    override fun invalidate() {
//        super.invalidate()
//    }
//}

class Pins @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TableLayout(context, attrs){

}