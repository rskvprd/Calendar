package com.example.calendar.base.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.calendar.R

sealed class Screens(
    @StringRes val nameId: Int,
    val route: String,
    val icon: ImageVector
) {
    object AccountScreen : Screens(
        nameId = R.string.account_screen_name,
        route = "account-screen",
        icon = Icons.Default.AccountCircle
    )

    object CalendarScreen : Screens(
        nameId = R.string.calendar_screen_name,
        route = "calendar-screen",
        icon = Icons.Default.CalendarMonth
    )

    companion object {
        val BOTTOM_BAR_SCREENS = listOf(
            CalendarScreen,
            AccountScreen,
        )
    }
}