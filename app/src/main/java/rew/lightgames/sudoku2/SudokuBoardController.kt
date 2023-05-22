package rew.lightgames.sudoku2

internal class SudokuBoardController {

        // 9x9 matrix representing the Sudoku board
        private val board = Array(9) { IntArray(9) { 0 } }

        // Initialize the board with the starting puzzle

        // Update the value at a specific position
        fun updateValue(row: Int, col: Int, value: Int) {
            // Validate the input (row, col, value)
            // Check if the position is not part of the initial puzzle

            // Update the value on the board
            board[row][col] = value

            // Check if the puzzle is solved
        }

        // Validate the input (row, col, value)
        private fun isValidInput(row: Int, col: Int, value: Int): Boolean {
            // Check if the value is valid for the row
            // Check if the value is valid for the column
            // Check if the value is valid for the sub-grid (3x3 box)

            return true
        }

        // Check if the puzzle is solved
        private fun isSolved(): Boolean {
            // Check if all rows, columns, and sub-grids (3x3 boxes) contain all numbers from 1 to 9

            return true
        }
}
