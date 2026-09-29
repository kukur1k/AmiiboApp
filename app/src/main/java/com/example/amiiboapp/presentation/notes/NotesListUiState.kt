package com.example.amiiboapp.presentation.notes

import com.example.amiiboapp.domain.model.Note


data class NotesListUiState(
    var notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)