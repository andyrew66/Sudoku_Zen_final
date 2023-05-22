package rew.lightgames.sudoku2

import android.content.Context
import android.util.Log
import com.opencsv.CSVReader

import java.io.InputStreamReader


class SudokuGenerator(private val context: Context) {

    private val size = 9
    private var puzzles: MutableList<Array<IntArray>> = mutableListOf()

    init {
        val inputStream = context.assets.open("easy.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
        val lines = reader.readAll()

        for (line in lines) {
            val puzzle = Array(size) { IntArray(size) }
            for (i in 0 until size) {
                for (j in 0 until size) {
                    puzzle[i][j] = line[0][i*size + j].toString().toInt()
                }
            }
            puzzles.add(puzzle)
        }
        reader.close()
        Log.d("puzzles","puzzles added $puzzles" )
    }
    fun getPuzzle(index: Int): Array<IntArray> {
        return puzzles[index]
        return when (index) {
            1 -> arrayOf(
                intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                intArrayOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                intArrayOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                intArrayOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                intArrayOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                intArrayOf(3, 4, 5, 2, 8, 6, 1, 0, 0)
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
                intArrayOf(7, 9, 6, 3, 1, 8, 4, 5, 0)
            )

            else -> throw IllegalArgumentException("Invalid index provided. Available indexes are 1 and 2.")
        }
    }

    fun getSolution(index: Int): Array<IntArray>? {

        return(solve(puzzles[index]))
        return when (index) {
            1 -> arrayOf(
                intArrayOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                intArrayOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                intArrayOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                intArrayOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
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

    fun getNumberOfPuzzles(): Int {
        return puzzles.size-1 // replace this with the actual number of puzzles you have
    }


    fun solve(inputPuzzle: Array<IntArray>): Array<IntArray>? {
        val puzzle = inputPuzzle.map { it.clone() }.toTypedArray()
        if (!solveInPlace(puzzle)) {
            return null
        }
        return puzzle
    }

    private fun solveInPlace(puzzle: Array<IntArray>): Boolean {
        for (row in 0 until size) {
            for (col in 0 until size) {
                if (puzzle[row][col] == 0) {
                    for (num in 1..size) {
                        if (isValid(puzzle, row, col, num)) {
                            puzzle[row][col] = num
                            if (solveInPlace(puzzle)) {
                                return true
                            } else {
                                puzzle[row][col] = 0
                            }
                        }
                    }
                    return false
                }
            }
        }
        return true
    }


    private fun isValid(puzzle: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        // check the row
        for (i in 0 until size) {
            if (puzzle[row][i] == num) {
                return false
            }
        }
        // check the column
        for (i in 0 until size) {
            if (puzzle[i][col] == num) {
                return false
            }
        }
        // check the box
        val boxRow = row - row % 3
        val boxCol = col - col % 3
        for (i in boxRow until boxRow + 3) {
            for (j in boxCol until boxCol + 3) {
                if (puzzle[i][j] == num) {
                    return false
                }
            }
        }
        // the number is valid
        return true
    }
}