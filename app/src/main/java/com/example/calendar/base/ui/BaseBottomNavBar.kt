package com.example.calendar.base.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.calendar.base.navigation.Screens

@Composable
fun BaseBottomNavBar(
    navHostController: NavHostController,
) {
    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar() {
        Screens.BOTTOM_BAR_SCREENS.mapIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navHostController.navigate(screen.route)
                },
                icon = { Icon(screen.icon, null) },
                label = { Text(text = stringResource(id = screen.nameId)) }
            )
        }
    }
}