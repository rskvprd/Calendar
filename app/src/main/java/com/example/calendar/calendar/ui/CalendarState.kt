package com.example.calendar.calendar.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.calendar.calendar.data.model.Note
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val showAddNoteDialog: Boolean = false,
    val newNoteContent: String = "",
    val currentNotes: List<Note> = emptyList(),
    val showEditNoteDialog: Boolean = false,
    val currentEditNoteContent: String = "",
    val currentEditNote: Note? = null,
)