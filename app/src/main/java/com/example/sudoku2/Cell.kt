package com.example.sudoku2

data class Cell(
    var isEditable: Boolean = true,
    val number: Int,
    val isHint: Boolean = false,
    val notes: MutableSet<Int> = mutableSetOf(),
    var original_number: Int
)
{
    init {

        isEditable = original_number == 0
    }
    fun addNote(note: Int) {
        if(notes.contains(note)){
            notes.remove(note)
        } else {
            notes.add(note)
        }
    }


}