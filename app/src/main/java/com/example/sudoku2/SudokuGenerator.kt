package com.example.sudoku2

class SudokuGenerator {

    private val size = 9

    fun getPuzzle(index: Int): Array<IntArray> {
        return when (index) {
            1 -> arrayOf(
                intArrayOf(5, 3, 0, 0, 7, 0, 0, 0, 0),
                intArrayOf(6, 0, 0, 1, 9, 5, 0, 0, 0),
                intArrayOf(0, 9, 8, 0, 0, 0, 0, 6, 0),
                intArrayOf(8, 0, 0, 0, 6, 0, 0, 0, 3),
                intArrayOf(4, 0, 0, 8, 0, 3, 0, 0, 1),
                intArrayOf(7, 0, 0, 0, 2, 0, 0, 0, 6),
                intArrayOf(0, 6, 0, 0, 0, 0, 2, 8, 0),
                intArrayOf(0, 0, 0, 4, 1, 9, 0, 0, 5),
                intArrayOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
            )
            2 -> arrayOf(
                intArrayOf(8, 0, 0, 0, 0, 0, 0, 0, 0),
                intArrayOf(0, 0, 3, 6, 0, 0, 0, 0, 0),
                intArrayOf(0, 7, 0, 0, 9, 0, 2, 0, 0),
                intArrayOf(0, 5, 0, 0, 0, 7, 0, 0, 0),
                intArrayOf(0, 0, 0, 0, 4, 5, 7, 0, 0),
                intArrayOf(0, 0, 0, 1, 0, 0, 0, 3, 0),
                intArrayOf(0, 0, 1, 0, 0, 0, 0, 6, 8),
                intArrayOf(0, 0, 8, 5, 0, 0, 0, 1, 0),
                intArrayOf(0, 9, 0, 0, 0, 0, 4, 0, 0)
            )
            else -> throw IllegalArgumentException("Invalid index provided. Available indexes are 1 and 2.")
        }
    }

    fun getSolution(index: Int): Array<IntArray> {
        return when (index) {
            1 -> arrayOf(
                intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),                intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                intArrayOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
            )
            2 -> arrayOf(
                intArrayOf(8, 1, 2, 7, 5, 3, 6, 4, 9),
                intArrayOf(9, 4, 3, 6, 8, 2, 1, 7, 5),
                intArrayOf(6, 7, 5, 4, 9, 1, 2, 8, 3),
                intArrayOf(1, 5, 4, 2, 3, 7, 8, 9, 6),
                intArrayOf(3, 6, 9, 8, 4, 5, 7, 2, 1),
                intArrayOf(2, 8, 7, 1, 6, 9, 5, 3, 4),
                intArrayOf(5, 2, 1, 9, 7, 4, 3, 6, 8),
                intArrayOf(4, 3, 8, 5, 2, 6, 9, 1, 7),
                intArrayOf(7, 9, 6, 3, 1, 8, 4, 5, 2)
            )
            else -> throw IllegalArgumentException("Invalid index provided. Available indexes are 1 and 2.")
        }
    }
}


