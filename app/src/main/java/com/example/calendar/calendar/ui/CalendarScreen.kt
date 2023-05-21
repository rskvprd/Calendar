package com.example.calendar.calendar.ui

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.calendar.R
import com.example.calendar.calendar.data.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel
) {

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::showAddNoteDialog) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.state.showAddNoteDialog) {
                    AddEditNoteDialog(
                        onDismiss = viewModel::dismissAddNoteDialog,
                        onConfirmButton = viewModel::onAddNote,
                        content = viewModel.state.newNoteContent,
                        onChangeContent = viewModel::onChangeNewNoteContent,
                        isEdit = false
                    )
                }
                if (viewModel.state.showEditNoteDialog) {
                    AddEditNoteDialog(
                        onDismiss = viewModel::dismissEditNoteDialog,
                        onConfirmButton = viewModel::onEditNote,
                        content = viewModel.state.currentEditNoteContent,
                        onChangeContent = viewModel::onChangeEditNoteContent,
                        isEdit = true
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    AndroidView(factory = { context ->
                        CalendarView(context)
                    }, update = { calendarView ->
                        calendarView.setOnDateChangeListener(viewModel::onDateChange)
                    })
                }

                NoteList(
                    viewModel.state.currentNotes,
                    viewModel::showEditNoteDialog,
                    viewModel::deleteNote
                )
            }
        }

    }
}

@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Note) -> Unit, onDelete: (Note) -> Unit) {
    Text(
        text = stringResource(id = R.string.added_notes_title),
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(vertical = 20.dp)
    )
    if (notes.isEmpty()) {
        Text(text = stringResource(id = R.string.on_this_day_no_notes))
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        notes.forEach { note ->
            NoteItem(
                note = note,
                onDelete = { onDelete(note) },
                onNoteClick = { onNoteClick(note) }
            )
        }
    }
}

@Composable
fun NoteItem(note: Note, onNoteClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 30.dp)
            .fillMaxWidth()
            .clickable(onClick = onNoteClick), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(text = note.text, modifier = Modifier.padding(20.dp), color = Color.Black)
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { onDelete() }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteDialog(
    onDismiss: () -> Unit,
    onConfirmButton: () -> Unit,
    content: String,
    onChangeContent: (String) -> Unit,
    isEdit: Boolean,
) {
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(onClick = onConfirmButton) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }, dismissButton = {
        Button(onClick = onDismiss) {
            Text(text = stringResource(id = R.string.cancel))
        }
    }, title = {
        val titleId = if (isEdit) {
            R.string.edit_note_dialog_title
        } else {
            R.string.add_note_dialog_title
        }
        Text(
            text = stringResource(id = titleId),
        )
    }, text = {
        Column() {
            val instructionId = if (isEdit) {
                R.string.edit_note_instruction
            } else {
                R.string.add_note_instruction
            }
            Text(text = stringResource(id = instructionId))
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(200.dp),
                maxLines = 5,
                singleLine = false,
                value = content,
                onValueChange = onChangeContent,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                label = { Text(text = stringResource(id = R.string.note_label)) }
            )
        }

    })
}

