package rew.lightgames.sudoku2

data class SavedGameState(
    val board: SudokuBoard,
    val hintsUsed: Int,
    val timeElapsed: Int,
)