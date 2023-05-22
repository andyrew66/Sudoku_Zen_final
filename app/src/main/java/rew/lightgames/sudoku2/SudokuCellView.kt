package rew.lightgames.sudoku2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.ceil
import kotlin.math.floor


class SudokuCellView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val textSize: Float = resources.getDimension(R.dimen.cell_text_size)

    private var cell: Cell? = null
    fun setCell(cell: Cell) {
        this.cell = cell
        invalidate() // Redraw the view
    }
    private val notePaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
        textSize = 90f
    }

    private val notesPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
        textSize = 1f
    }


    init {

        notePaint.textSize = textSize
        notePaint.color = Color.BLACK

        notesPaint.textSize = textSize / 3f // Set the notes text size smaller
        notesPaint.color = Color.BLACK

        setBackgroundColor(Color.WHITE)
        bringToFront()
    }


    private var cellText: String = ""
    val number = cellText.trim().toIntOrNull()
    fun setText(text: String) {
        cellText = text
        cell?.number = text.trim().toIntOrNull() ?: 0
        invalidate() // Redraw the view
    }
    private var notesText: String = ""
    fun setNotes(text: String){
        notesText = text
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        if(cellText != "0"){
            drawNumber(canvas)
        } else {
            drawCellNotes(canvas)
        }



    }

    private fun drawCellNotes(canvas: Canvas?) {

        val cellSize = width / 3f // adjust this value to match your needs
        val numRowsCols = 3
        notesPaint.textSize = cellSize * 0.8f // Adjust the size based on your preference
        notesText.forEach { charNote ->
            val note = charNote.toString().toIntOrNull() // Convert Char to Int
            if (note != null) {
                val row = ceil(note / numRowsCols.toDouble()).toInt() - 1
                val col = (note - 1) % numRowsCols
                val centerX = col * cellSize + cellSize / 2
                val centerY = row * cellSize + cellSize / 2 - (notesPaint.descent() + notesPaint.ascent()) / 2
                canvas?.drawText(note.toString(), centerX, centerY, notesPaint)
            }
        }
    }





    private fun drawNumber(canvas: Canvas) {
        val textSize = width * 0.8f // Adjust the size based on your preference
        notePaint.textSize = textSize

        // Calculate the position for the number in the cell
        val textX = width / 2f
        val textY = height / 2f - (notePaint.descent() + notePaint.ascent()) / 2

        // Check if cellText is a valid integer
        if (cellText.trim().isNotEmpty()) {
            // Draw the number
            canvas.drawText(cellText, textX, textY, notePaint)
        }
    }

}


