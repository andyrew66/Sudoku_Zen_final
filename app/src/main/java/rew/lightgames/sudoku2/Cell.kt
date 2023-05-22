package rew.lightgames.sudoku2

import java.io.Serializable

data class Cell(
    var isEditable: Boolean = true,
    var number: Int,
    val isHint: Boolean = false,
    val notes: ArrayList<Int> = arrayListOf(),
    var original_number: Int
): Serializable {
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

    fun clearNotes(){
        notes.clear()
    }
}
