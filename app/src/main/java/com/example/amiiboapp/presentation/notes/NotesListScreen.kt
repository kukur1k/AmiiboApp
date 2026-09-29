package com.example.amiiboapp.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.presentation.amiiboList.AmiiboListViewModel


@Composable
fun NotesListScreen(viewModel: NotesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    val notes = uiState.notes
    notes.forEach { note ->
        Row(modifier = Modifier.padding(10.dp)) {
            Text(text = note.text)
        }
    }
}
