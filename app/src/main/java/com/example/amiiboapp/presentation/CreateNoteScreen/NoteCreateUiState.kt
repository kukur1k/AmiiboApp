package com.example.amiiboapp.presentation.CreateNoteScreen

import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.usecase.NoteCreateErrors


data class NoteCreateUiState(
    var amiiboId: String = "",
    val text: String = "",
    val rating: String = "0",
    val imagePath: String = "",
    val errors: NoteCreateErrors? = null
)