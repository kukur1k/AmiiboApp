package com.example.amiiboapp.presentation.CreateNoteScreen

import com.example.amiiboapp.domain.model.Note


data class NoteCreateUiState(
    var amiiboId: String = "",
    val text: String = "",
    val rating: String = "0",
    val errors: String = ""
)