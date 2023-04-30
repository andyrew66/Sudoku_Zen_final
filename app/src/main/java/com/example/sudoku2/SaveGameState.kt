package com.example.sudoku2

data class SavedGameState(
    val board: Int,
    val solution: Int,
    val hintsUsed: Int,
    // ... any other properties you need to save
)