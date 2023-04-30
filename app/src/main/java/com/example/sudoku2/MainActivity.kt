package com.example.sudoku2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import android.os.Handler
import android.os.Looper
import android.widget.TextView


class MainActivity : AppCompatActivity(), SudokuControlListener, OnCellSelectedListener, TimerListener {
    private lateinit var timer: Timer
    private val viewModel: SudokuViewModel by viewModels()
    lateinit var sudokuControlView: SudokuControlView
    lateinit var sudokuBoardView: SudokuBoardView
    lateinit var timerTextView: TextView
    lateinit var hintsCountTextView: TextView
    lateinit var modeTextView: TextView

    var notesMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.hintsUsed.observe(this, Observer { hintsUsed ->
            val hintsCountText = "Hints used: $hintsUsed"
            hintsCountTextView.text = hintsCountText
        })
        setContentView(R.layout.sudoku_board_view)

        sudokuBoardView = findViewById<SudokuBoardView>(R.id.sudokuBoardView)
        sudokuControlView = findViewById(R.id.sudokuControlView) // Initialize the view here
        sudokuControlView.listener = this
        sudokuBoardView.cellSelectedListener = this

        timerTextView = findViewById(R.id.timerTextView)
        hintsCountTextView = findViewById(R.id.hintsCountTextView)
        modeTextView = findViewById(R.id.modeTextView)



        viewModel.sudokuBoard.observe(this, Observer { board ->
            sudokuBoardView.setBoard(board)
            val cell = viewModel.selectedCell.value
            if (cell != null) {
                val row = cell.first
                val col = cell.second
                val value = board?.getCell(row, col)
                if (value != null) {
                    sudokuBoardView.updateSelectedCell(row, col, value)
                }
            }
        })

        timer = Timer(this)
        timer.start()
    }

    override fun onPause() {
        super.onPause()
        timer.pause()
    }

    override fun onResume() {
        super.onResume()
        timer.start()
    }

    override fun onTimerUpdate(seconds: Int) {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        val timerText = String.format("%02d:%02d", minutes, remainingSeconds)
        updateTextViews(timerText, viewModel.hintsUsed.value ?: 0, if (notesMode) "Notes Mode" else "Normal Mode")

    }


    // ... the rest of the MainActivity class

    override fun onNotesModeChanged(notesMode: Boolean) {
        this.notesMode = !this.notesMode
        // You can update the UI or other elements based on the notesMode
    }
    override fun onNumberButtonClicked(value: Int) {
        Log.d("MainActivity", "Number button clicked: $value")
        viewModel.updateSelectedCellValue(value)
    }

    override fun onCellSelected(row: Int, col: Int) {
        Log.d("MainActivity", "Cell selected: row=$row, col=$col")
        viewModel.selectCell(row, col)
    }
    override fun onEraseButtonClicked() {
        Log.d("MainActivity", "Erase button clicked")
        viewModel.updateSelectedCellValue(0)
    }

    override fun onHintsButtonClicked() {
        Log.d("MainActivity","HintbuttonClicked")
        viewModel.provideHint()
        viewModel.toggleNotesMode()
        viewModel.selectCell(-1,-1)

    }

    fun updateTextViews(timerText: String, hintsCount: Int, modeText: String) {
        timerTextView.text = timerText
        hintsCountTextView.text = "Hints used: $hintsCount"
        modeTextView.text = modeText
    }


}

interface TimerListener {
    fun onTimerUpdate(seconds: Int)
}