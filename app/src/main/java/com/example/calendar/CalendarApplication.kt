package com.example.calendar

import android.app.Application
import com.google.firebase.FirebaseApp

class CalendarApplication : Application() {
    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}