package com.example.calendar.calendar.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Note(
    @PrimaryKey val id: Long? = null,
    val date: LocalDate,
    val text: String
)