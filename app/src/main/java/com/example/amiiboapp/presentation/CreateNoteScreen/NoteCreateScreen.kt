package com.example.amiiboapp.presentation.CreateNoteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun NoteCreateScreen(viewModel: NoteCreateViewModel = hiltViewModel(),
               amiiboId: String) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    uiState.amiiboId = amiiboId

    Column(modifier = Modifier.padding(12.dp)){
        OutlinedTextField(
            value = uiState.text,
            onValueChange = { newValue ->
                viewModel.updateState(uiState.copy(text = newValue))
            },
            label = { Text("текст") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.rating,
            onValueChange = { newValue ->
                viewModel.updateState(uiState.copy(rating = newValue))
            },
            label = { Text("Личный рейтинг") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.insertNote(uiState.amiiboId, uiState.text, uiState.rating.toInt()) },
            shape = RoundedCornerShape(size = 15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3361E0)
            )
        ) {
            Text("Создать заметку")
        }
    }





}
