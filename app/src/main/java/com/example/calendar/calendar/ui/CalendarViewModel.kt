package com.example.calendar.calendar.ui

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calendar.calendar.data.dao.NoteDao
import com.example.calendar.calendar.data.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(private val noteDao: NoteDao) : ViewModel() {
    var state by mutableStateOf(CalendarState())

    init {
        val currentDate = LocalDate.now()
        loadNotesByDate(currentDate)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteDao.deleteNote(note)
            }
        }
        deleteNoteInCurrentList(note)
    }

    fun onDateChange(calendarView: CalendarView, year: Int, month: Int, date: Int) {
        val selectedDate = LocalDate.of(year, month + 1, date)
        loadNotesByDate(selectedDate)
    }

    fun showAddNoteDialog() {
        state = state.copy(
            showAddNoteDialog = true,
        )
    }

    fun dismissAddNoteDialog() {
        state = state.copy(
            showAddNoteDialog = false,
        )
    }

    fun onChangeNewNoteContent(newContent: String) {
        state = state.copy(
            newNoteContent = newContent
        )
    }

    fun onAddNote() {
        val text = state.newNoteContent
        val newNote = Note(date = state.selectedDate, text = text)
        viewModelScope.launch {
            val id = withContext(Dispatchers.IO) {
                noteDao.addNewNote(newNote)
            }
            state = state.copy(
                currentNotes = state.currentNotes + newNote.copy(id = id),
                showAddNoteDialog = false,
                newNoteContent = ""
            )
        }
    }

    fun showEditNoteDialog(note: Note) {
        state = state.copy(
            showEditNoteDialog = true,
            currentEditNote = note,
            currentEditNoteContent = note.text,
        )
    }

    fun dismissEditNoteDialog() {
        state = state.copy(
            showEditNoteDialog = false,
            currentEditNote = null,
            currentEditNoteContent = ""
        )
    }

    fun onChangeEditNoteContent(text: String) {
        state = state.copy(
            currentEditNoteContent = text
        )
    }

    fun onEditNote() {
        viewModelScope.launch {
            val editedNote = state.currentEditNote!!.copy(text = state.currentEditNoteContent)
            editNoteInCurrentList(editedNote)
            withContext(Dispatchers.IO) {
                noteDao.updateNote(editedNote)
            }
            state = state.copy(
                showEditNoteDialog = false,
            )
        }
    }

    private fun deleteNoteInCurrentList(note: Note) {
        val newNotes = state.currentNotes.toMutableList()
        newNotes.remove(note)
        state = state.copy(
            currentNotes = newNotes
        )
    }

    private fun editNoteInCurrentList(note: Note) {
        val newNotes = state.currentNotes.map {
            if (it.id == note.id) {
                note
            } else {
                it
            }
        }
        state = state.copy(
            currentNotes = newNotes
        )
    }

    private fun loadNotesByDate(date: LocalDate) {
        viewModelScope.launch {
            val currentNotes =
                withContext(Dispatchers.IO) {
                    noteDao.getByDate(date)
                }
            state = state.copy(
                selectedDate = date,
                currentNotes = currentNotes
            )
        }
    }
}

class CalendarViewModelFactory(
    private val noteDao: NoteDao
) : ViewModelProvider.NewInstanceFactory() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarViewModel(noteDao) as T
    }
}
