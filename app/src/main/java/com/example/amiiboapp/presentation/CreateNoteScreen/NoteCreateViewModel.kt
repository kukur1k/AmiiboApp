package com.example.amiiboapp.presentation.CreateNoteScreen;


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.repository.AmiiboRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class NoteCreateViewModel @Inject constructor(
        private val repository: AmiiboRepository
): ViewModel() {
        private val _uiState = MutableStateFlow(NoteCreateUiState())
        val uiState: StateFlow<NoteCreateUiState> = _uiState.asStateFlow()


        fun updateState(newState: NoteCreateUiState) {
                _uiState.value = newState
        }

        fun insertNote(amiiboId: String, text: String, rating: Int) = viewModelScope.launch {
                withContext(Dispatchers.IO){
                        repository.AddNote(
                                uiState.value.amiiboId,
                                uiState.value.text,
                                uiState.value.rating.toInt())
                }
        }


}
