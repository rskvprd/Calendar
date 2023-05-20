package com.example.calendar.calendar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.calendar.calendar.data.model.Note
import java.time.LocalDate

@Dao
interface NoteDao {
    @Insert
    fun addNewNote(note: Note): Long

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("""SELECT * FROM note WHERE date = :date""")
    fun getByDate(date: LocalDate): List<Note>

    @Query("""SELECT * FROM note""")
    fun getAll(): List<Note>
}