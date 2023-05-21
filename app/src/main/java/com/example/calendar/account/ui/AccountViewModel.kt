package com.example.calendar.account.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountViewModel : ViewModel() {
    var state by mutableStateOf(AccountState())

    init {
        updateCurrentUser()
    }

    fun onEmailChange(email: String) {
        state = state.copy(
            email = email
        )
    }

    fun onPasswordChange(password: String) {
        state = state.copy(
            password = password
        )
    }

    fun signUp() {
        Firebase.auth.createUserWithEmailAndPassword(state.email, state.password).addOnCompleteListener {
            updateCurrentUser()
            if (it.exception != null) {
                state = state.copy(error = it.exception!!.localizedMessage)
            }
        }
    }

    fun signIn() {
        Firebase.auth.signInWithEmailAndPassword(state.email, state.password).addOnCompleteListener {
            if (it.exception != null) {
                state = state.copy(error = it.exception!!.localizedMessage)
            }
            updateCurrentUser()
        }
    }

    fun signOut() {
        Firebase.auth.signOut()
        updateCurrentUser()
    }

    fun dismissError() {
        state = state.copy(
            error = null
        )
    }

    private fun updateCurrentUser() {
        state = state.copy(
            currentUser = Firebase.auth.currentUser
        )
    }
}