package com.example.amiiboapp.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.amiiboapp.R
import com.example.amiiboapp.domain.model.Note
import coil3.compose.AsyncImage
import com.example.amiiboapp.navigation.Screen
import com.example.amiiboapp.presentation.amiiboList.AmiiboItem
import com.example.amiiboapp.presentation.amiiboList.AmiiboListViewModel


@Composable
fun NotesListScreen(viewModel: NotesViewModel = hiltViewModel(), navController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    val notes = uiState.notes

    LazyColumn {
        items(notes, key = {it.id}){ note ->
            NoteItem(
                note = note
            )
        }
    }
}



@Composable
fun NoteItem(note: Note){
    Column() {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(text = note.text, modifier = Modifier.padding(10.dp))
            Text(text = note.amiiboId, modifier = Modifier.padding(10.dp))
        }
        AsyncImage(
            model = note.coverImagePath ?: R.drawable.logo,
            contentDescription = "фото",
            modifier = Modifier.size(56.dp),
            contentScale = ContentScale.Crop
        )
    }

}

