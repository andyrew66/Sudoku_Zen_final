package rew.lightgames.sudoku2

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class BorderDrawable(private val strokeWidth: Float, private val thickStrokeWidth: Float, private val color: Int, private val backgroundColor: Int) : Drawable() {
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
    }
    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        backgroundPaint.color = backgroundColor
        canvas.drawRect(bounds, backgroundPaint)

        paint.strokeWidth = strokeWidth
        paint.color = color
        canvas.drawRect(bounds, paint)

        paint.strokeWidth = strokeWidth
        canvas.drawLine(bounds.left.toFloat(), bounds.bottom.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), paint)
        canvas.drawLine(bounds.right.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}
