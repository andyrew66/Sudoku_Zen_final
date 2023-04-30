package com.example.sudoku2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class SudokuViewModel : ViewModel() {
    private val _sudokuBoard = MutableLiveData<SudokuBoard>().apply {
        value = SudokuBoard(
            cells = Array(9) { row ->
                Array(9) { col ->
                    Cell(isEditable = false, number = 0, original_number = 0)
                }
            },
            solution = Array(9) { IntArray(9) { 0 } }
        ) // Initialize the LiveData with a new SudokuBoard instance
    }
    private val _hintsUsed = MutableLiveData<Int>(0)
    val hintsUsed: LiveData<Int> get() = _hintsUsed
    val sudokuBoard: LiveData<SudokuBoard> = _sudokuBoard
    private var notesMode =false
    private val _selectedCell = MutableLiveData<Pair<Int, Int>>()
    val selectedCell: LiveData<Pair<Int, Int>> = _selectedCell

    init {
        generateSudoku()
    }

    fun selectCell(row: Int, col: Int) {
        _selectedCell.value = Pair(row, col)
    }

    fun updateSelectedCellValue(value: Int) {
        _selectedCell.value?.let { (row, col) ->
            if (row == -1 || col == -1) {
                return
            }

            _sudokuBoard.value = _sudokuBoard.value?.apply {
                val currentCell = getCell(row, col)
                if (currentCell.isEditable) {
                    if (notesMode) {
                        // Toggle the note in the cell
                        val newNotes = currentCell.notes.toMutableSet()
                        if (newNotes.contains(value)) {
                            newNotes.remove(value)
                        } else {
                            newNotes.add(value)
                        }
                        val newCell = currentCell.copy(notes = newNotes)
                        setCell(row, col, newCell)
                    } else {
                        // Update the cell value as before
                        val newCell = currentCell.copy(number = value, original_number = 0)
                        setCell(row, col, newCell)
                    }
                    Log.d("SudokuViewModel", "Number button clicked: $value")
                }
            }
        }
    }



    private fun generateSudoku() {
        viewModelScope.launch(Dispatchers.IO) {
            val sudokuGenerator = SudokuGenerator()
            val solvedGrid = sudokuGenerator.getSolution(1)
            val partiallySolvedGrid = sudokuGenerator.getPuzzle(1)
            updateUI(partiallySolvedGrid, solvedGrid)
        }
    }

    private fun updateUI(partiallySolvedGrid: Array<IntArray>, solution: Array<IntArray>) {
        viewModelScope.launch(Dispatchers.Main) {
            val newBoard = SudokuBoard(
                cells = partiallySolvedGrid.map { row ->
                    row.map { number ->
                        Cell(number = number, original_number = number)
                    }.toTypedArray()
                }.toTypedArray(),
                solution = solution
            )

            _sudokuBoard.value = newBoard
        }
    }

    fun provideHint() {
        _selectedCell.value?.let { (row, col) ->
            if(row == -1 || col == -1){
                return
            }
            val solutionValue = _sudokuBoard.value?.solution?.get(row)?.get(col)
            solutionValue?.let { value ->
                _sudokuBoard.value = _sudokuBoard.value?.apply {
                    val cell = getCell(row, col).copy(number = value, isHint = true, original_number = value)
                    setCell(row, col, cell)
                    Log.d("SudokuViewModel", "Hint provided for cell ($row, $col): $value")
                    _hintsUsed.value = _hintsUsed.value?.plus(1)
                }
                deselectCell()
            }
        }
        deselectCell() // Deselect the cell after providing a hint
    }

    private fun deselectCell() {
        _selectedCell.value = Pair(-1, -1)
    }
    fun toggleNotesMode() {
        notesMode = !notesMode
    }

    fun addNoteToSelectedCell(value: Int) {
        selectedCell.value?.let { (row, col) ->
            sudokuBoard.value?.addNote(row, col, value)
            _sudokuBoard.postValue(sudokuBoard.value)
        }
    }

}

