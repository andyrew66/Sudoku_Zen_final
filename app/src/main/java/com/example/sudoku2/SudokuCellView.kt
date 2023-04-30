package com.example.sudoku2
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
class SudokuCellView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val textSize: Float = resources.getDimension(R.dimen.cell_text_size)

    private val notePaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
        textSize = 90f

    }

    init {
        notePaint.textSize = textSize
        notePaint.color = Color.RED
        setBackgroundColor(Color.WHITE)
        bringToFront()
    }

    private var cellText: String = ""

    fun setText(text: String) {
        cellText = text
        invalidate() // Redraw the view
    }

    var number: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var notes: Set<Int> = setOf()
        set(value) {
            field = value
            invalidate()
        }

    var notesMode: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!notesMode && number == 0) {
            drawNotes(canvas)
            Log.d("SudokuBoardView", "onDraw called")
        } else {
            drawNumber(canvas)
            Log.d("SudokuBoardView", "onDraw called")
        }
    }

    private fun drawNotes(canvas: Canvas) {
        val noteSize = width / 3f
        val noteTextSize = noteSize * 0.6f
        notePaint.textSize = noteTextSize

        for (i in 0 until 9) {
            val noteValue = i + 1
            if (notes.contains(noteValue)) {
                val noteRow = i / 3
                val noteCol = i % 3
                val noteX = noteCol * noteSize + noteSize / 2
                val noteY = noteRow * noteSize + noteSize / 2 + noteTextSize / 2
                canvas.drawText(noteValue.toString(), noteX, noteY, notePaint)
            }
        }
    }

    private fun drawNumber(canvas: Canvas) {
        val textSize = width * 0.8f // Adjust the size based on your preference
        notePaint.textSize = textSize

        Log.d("drawnumber","Draw Number called")
        // Calculate the position for the number in the cell
        val textX = width / 2f
        val textY = height / 2f - (notePaint.descent() + notePaint.ascent()) / 2

        // Draw the number
        canvas.drawText(cellText, textX, textY, notePaint)
    }
}
