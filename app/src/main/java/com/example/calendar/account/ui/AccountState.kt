package com.example.calendar.account.ui

import com.google.firebase.auth.FirebaseUser

data class AccountState(
    val email: String = "",
    val password: String = "",
    val currentUser: FirebaseUser? = null,
    val error: String? = null
)