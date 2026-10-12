package com.example.amiiboapp.presentation.CreateNoteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.repository.AmiiboRepository
import com.example.amiiboapp.domain.usecase.NoteCreateErrors
import com.example.amiiboapp.domain.usecase.ValidateNoteUseCase
import com.example.amiiboapp.domain.usecase.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteCreateViewModel @Inject constructor(
        private val repository: AmiiboRepository
) : ViewModel() {

        private val _uiState = MutableStateFlow(NoteCreateUiState())
        val uiState: StateFlow<NoteCreateUiState> = _uiState.asStateFlow()

        fun updateState(newState: NoteCreateUiState) {
                _uiState.value = newState
        }

        fun insertNote(amiiboId: String){

                viewModelScope.launch {
                        val result = runCatching {
                                withContext(Dispatchers.IO) {
                                        val newNote = Note(amiiboId = amiiboId,
                                                text = uiState.value.text,
                                                ratingNote = uiState.value.rating.toInt(),
                                                coverImagePath = uiState.value.imagePath)

                                        val validator = ValidateNoteUseCase()
                                        val result = validator(newNote)
                                        if (!result.isValid){
                                                _uiState.update { it.copy(errors = result.errors) }
                                        } else{
                                                viewModelScope.launch {
                                                        repository.AddNote(newNote)
                                                        _uiState.update { it.copy(errors = NoteCreateErrors()) }
                                                        _uiState.update { it.copy(text = "", rating = "", imagePath = "") }
                                                }
                                        }





                                }
                        }
                }
        }

        fun onImageSelected(uri: String, amiiboId: String) {
                viewModelScope.launch {
                        val result = runCatching {
                                withContext(Dispatchers.IO) {
                                        repository.addCoverImage(amiiboId, uri)
                                }
                        }
                        _uiState.update {
                                result.fold(
                                        onSuccess = { savedPath -> it.copy(imagePath = savedPath) },
                                        onFailure = { e -> it.copy(errors = NoteCreateErrors(emptyText = "Ошибка загрузки фото: ${e.toString()}")) }
                                )
                        }
                }
        }


        fun GetErrorsLine(errors: NoteCreateErrors?): String? {

                var error: String? = ""
                var isValid = true


                if (errors?.starsCount != null){
                        error = errors.starsCount
                        isValid = false
                }

                if (errors?.emptyText != null){
                        error = errors.emptyText
                        isValid = false
                }

                if (errors?.emptyText == null && errors?.starsCount == null && !isValid) return ""

                if (isValid == true){
                        error = "Заметка успешно создана"
                }
                return error
        }

}