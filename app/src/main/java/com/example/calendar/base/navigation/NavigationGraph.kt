package com.example.calendar.base.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import com.example.calendar.account.ui.AccountScreen
import com.example.calendar.account.ui.AccountViewModel
import com.example.calendar.calendar.data.database.NoteDatabase
import com.example.calendar.calendar.ui.CalendarScreen
import com.example.calendar.calendar.ui.CalendarViewModel
import com.example.calendar.calendar.ui.CalendarViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navHostController: NavHostController, context: Context) {
    val db = Room.databaseBuilder(
        context,
        NoteDatabase::class.java, "database-name"
    ).build()

    val noteDao = db.noteDao()

    val calendarViewModel: CalendarViewModel =
        viewModel(factory = CalendarViewModelFactory(noteDao))
    val accountViewModel: AccountViewModel =
        viewModel()



    NavHost(navController = navHostController, startDestination = Screens.CalendarScreen.route) {
        composable(route = Screens.CalendarScreen.route) {
            CalendarScreen(
                viewModel = calendarViewModel
            )
        }

        composable(route = Screens.AccountScreen.route) {
            AccountScreen(
                viewModel = accountViewModel
            )
        }
    }
}