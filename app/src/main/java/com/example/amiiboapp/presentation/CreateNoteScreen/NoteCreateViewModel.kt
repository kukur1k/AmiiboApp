package com.example.amiiboapp.presentation.CreateNoteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

        fun insertNote(amiiboId: String, text: String, rating: Int, imagePath: String){

                viewModelScope.launch {
                        val result = runCatching {
                                withContext(Dispatchers.IO) {
                                        repository.AddNote(amiiboId, text, rating, imagePath)
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
//                        _uiState.update {
//                                result.fold(
//                                        onSuccess = { savedPath -> it.copy(imagePath = savedPath) },
//                                        onFailure = { e -> it.copy(errors = "Ошибка сохранения фото: ${e.message}") }
//                                )
//                        }
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

                if (isValid == true){
                        error = "Заметка успешно создана"
                }
                return error
        }

}