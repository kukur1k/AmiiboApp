package com.example.amiiboapp.presentation.notes;


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amiiboapp.domain.repository.AmiiboRepository;
import com.example.amiiboapp.presentation.amiiboList.AmiiboListUiState

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotesViewModel @Inject constructor(
        private val repository: AmiiboRepository
): ViewModel() {
        private val _uiState = MutableStateFlow(NotesListUiState())
        val uiState: StateFlow<NotesListUiState> = _uiState.asStateFlow()

        init {
            loadNotes()
        }

        private fun loadNotes() = viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                var notes = repository.getAllNotes()
                _uiState.update { it.copy( notes = notes, isLoading = false) }
        }


}
