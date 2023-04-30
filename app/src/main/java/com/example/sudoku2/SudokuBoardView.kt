package com.example.sudoku2

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlin.random.Random
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
private const val originalColour = "#80A0A0A0"
private const val blueColour = "#80E0E0E0"
private const val whiteColour = "#80FFFFFF"
private val greenColor = Color.parseColor("#A080D480") // Adjust the color as desired
private val grayBorderColor = Color.parseColor("#808080")
private val highlightColor = Color.parseColor("#AA673AB7")
private val orangeColor = Color.parseColor("#A0FFA500")
interface OnCellSelectedListener {
    fun onCellSelected(row: Int, col: Int)
}

class SudokuBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels
    val screenHeight = displayMetrics.heightPixels
    val textSize = screenWidth * 0.015f // 1.5% of screen width

    private var board: SudokuBoard? = null
    var selectedRow = -1
    var selectedCol = -1
    var cellSelectedListener: OnCellSelectedListener? = null

    private val BORDER_WIDTH = 2
    private val THICK_BORDER_WIDTH = 5
    private val cells = Array(9) { arrayOfNulls<SudokuCellView>(9)
    }
    private val notePaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        isAntiAlias = true
    }

    init {


        val random = Random

        val gridLayout = GridLayout(context).apply {
            id = View.generateViewId()
            columnCount = 9
            rowCount = 9
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }
        ShapeDrawable(RectShape()).apply {
            paint.color = grayBorderColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = BORDER_WIDTH.toFloat()
        }



        addView(gridLayout)

        for (i in 0..80) {
            val row = i / 9
            val col = i % 9
            val boxRow = row / 3
            val boxCol = col / 3

            val randomNumber = random.nextInt(9) + 1

            val sudokuCellView = SudokuCellView(context, null).apply {
                id = View.generateViewId()

                // Set the background color and border
                val strokeWidth =
                    if (row % 3 == 2 || col % 3 == 2) THICK_BORDER_WIDTH else BORDER_WIDTH
                val boxRow = row / 3
                val boxCol = col / 3
                val backgroundColor = if ((boxRow + boxCol) % 2 == 0) {
                    Color.parseColor(blueColour)
                } else {
                    Color.parseColor(whiteColour)
                }
                background = createCellBackground(backgroundColor, strokeWidth)

                // Set the layout parameters for the GridLayout
                layoutParams = GridLayout.LayoutParams().apply {
                    width = screenWidth / 9 // set the width to 1/9th of the screen width
                    height = screenWidth / 9 // set the height to 1/9th of the screen width
                    columnSpec = GridLayout.spec(col, 1f) // set the column and row spec
                    rowSpec = GridLayout.spec(row, 1f)
                }


                cells[row][col] = this
                setText(randomNumber.toString())
            }

            gridLayout.addView(sudokuCellView)
            gridLayout.bringChildToFront(sudokuCellView)
            //gridLayout.bringToFront()
            sudokuCellView.setText(randomNumber.toString())
            setCellBorder()
        }
    }


    fun highlightRowColBox(row: Int, col: Int) {
        val boxRow = row / 3 * 3
        val boxCol = col / 3 * 3

        for (i in 0..8) {
            for (j in 0..8) {
                if (i != row || j != col) {
                    val cell = board?.getCell(i, j)
                    val editText = cells[i][j]
                    val boxRowTemp = i / 3 * 3
                    val boxColTemp = j / 3 * 3
                    val backgroundColor = if ((boxRowTemp + boxColTemp) % 2 == 0) {
                        Color.parseColor(blueColour)
                    } else {
                        Color.parseColor(whiteColour)
                    }

                    val strokeWidth =
                        if (i % 3 == 2 || j % 3 == 2) THICK_BORDER_WIDTH else BORDER_WIDTH

                    if (i == row || j == col || (i >= boxRow && i < boxRow + 3 && j >= boxCol && j < boxCol + 3)) {
                        if (cell?.isHint == true) {
                            editText?.background = createCellBackground(greenColor, strokeWidth)
                        } else if (cell?.isEditable != true) {
                            editText?.background =
                                createCellBackground(Color.parseColor(originalColour), strokeWidth)
                        } else {
                            editText?.background = createCellBackground(highlightColor, strokeWidth)
                        }
                    } else {
                        if (cell?.isHint == true) {
                            editText?.background = createCellBackground(greenColor, strokeWidth)
                        } else if (cell?.isEditable != true) {
                            editText?.background =
                                createCellBackground(Color.parseColor(originalColour), strokeWidth)
                        } else {
                            editText?.background =
                                createCellBackground(backgroundColor, strokeWidth)
                        }
                    }
                }
            }
        }

        // Set the selected cell's color to orange
        cells[row][col]?.background = createCellBackground(orangeColor, THICK_BORDER_WIDTH)
    }

    fun setBoard(board: SudokuBoard?) {
        this.board = board
        board?.let { newBoard ->
            for (i in 0..8) {
                for (j in 0..8) {
                    val cell = newBoard.getCell(i, j)
                    if (cell.number != 0) {


                        // Set background color for original numbers
                        if (!cell.isEditable) {
                            updateCellBackground(i, j)
                        }
                    } else {
                        cells[i][j]?.setText("1")
                    }

                    // Set background color for hint cells
                    updateCellBackground(i, j)
                    cells[i][j]?.setText(cell.number.toString())
                }
            }
        }
        invalidate()
    }


    // SudokuBoardView.kt
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val cellWidth = width / 9f
            val cellHeight = height / 9f

            val col = (event.x / cellWidth).toInt()
            val row = (event.y / cellHeight).toInt()
            if (board?.getCell(row, col)?.isEditable == true) {
                selectedRow = row
                selectedCol = col
                cellSelectedListener?.onCellSelected(row, col)
                highlightRowColBox(row, col)

                val cell = board?.getCell(row, col) ?: Cell(
                    isEditable = false,
                    number = 0,
                    original_number = 0
                )
                updateSelectedCell(row, col, cell)

                invalidate() // Redraw the view

                // Bring the view to the front
                bringToFront()
                cells[row][col]?.bringToFront()
            }
        }

        return true
    }

    fun updateSelectedCell(row: Int, col: Int, cell: Cell) {
        if (row in 0..8 && col in 0..8) {
            cells[row][col]?.apply {

                if (cell.number == 0) {
                    updateNotes(row, col, cell.notes)
                } else {
                    setText(cell.number.toString())
                }
                updateCellBackground(row, col)
            }
        }
    }



    private fun createCellBackground(backgroundColor: Int, strokeWidth: Int): LayerDrawable {
        val border = ShapeDrawable(RectShape()).apply {
            paint.color = grayBorderColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth.toFloat()
        }
        return LayerDrawable(arrayOf(border, ColorDrawable(backgroundColor)))
    }

    // Add this helper function inside the SudokuBoardView class
    private fun setCellBorder() {
        for (i in 0..8) {
            for (j in 0..8) {
                val row = i / 9
                val col = i % 9
                val boxRow = row / 3
                val boxCol = col / 3
                val strokeWidth =
                    if (row % 3 == 2 || col % 3 == 2) THICK_BORDER_WIDTH else BORDER_WIDTH

                val backgroundColor = if ((boxRow + boxCol) % 2 == 0) {
                    Color.parseColor(blueColour)
                } else {
                    Color.parseColor(whiteColour)
                }
                cells[i][j]?.background = createCellBackground(backgroundColor, strokeWidth)
            }
        }
    }

    private fun updateCellBackground(row: Int, col: Int) {
        val cell =
            board?.getCell(row, col) ?: Cell(isEditable = false, number = 0, original_number = 0)
        val strokeWidth = if (row % 3 == 2 || col % 3 == 2) THICK_BORDER_WIDTH else BORDER_WIDTH
        val boxRow = row / 3
        val boxCol = col / 3
        val backgroundColor = if ((boxRow + boxCol) % 2 == 0) {
            Color.parseColor(blueColour)
        } else {
            Color.parseColor(whiteColour)
        }

        if (row == selectedRow && col == selectedCol) {
            cells[row][col]?.background = createCellBackground(orangeColor, strokeWidth)
        } else if (cell.isHint) {
            cells[row][col]?.background = createCellBackground(greenColor, strokeWidth)
        } else if (!cell.isEditable) {
            cells[row][col]?.background =
                createCellBackground(Color.parseColor(originalColour), strokeWidth)
        } else {
            cells[row][col]?.background = createCellBackground(backgroundColor, strokeWidth)
        }
    }
    fun updateNotes(row: Int, col: Int, notes: Set<Int>) {
        val notesString = StringBuilder()
        for (i in 1..9) {
            if (notes.contains(i)) {
                notesString.append(i)
            } else {
                notesString.append(" ")
            }
            if (i % 3 == 0 && i != 9) {
                notesString.append("\n")
            }
        }
        cells[row][col]?.setText(notesString.toString())
    }

}