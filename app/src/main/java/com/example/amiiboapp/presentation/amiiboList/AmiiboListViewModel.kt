package com.example.amiiboapp.presentation.amiiboList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.repository.AmiiboRepository
import com.example.amiiboapp.domain.usecase.GetSortedAmiibosUseCase
import com.example.amiiboapp.domain.usecase.SortOrder
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
class AmiiboListViewModel @Inject constructor (
    private val getSortedAmiibosUseCase: GetSortedAmiibosUseCase,
    private val repository: AmiiboRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboListUiState())
    val uiState: StateFlow<AmiiboListUiState> = _uiState.asStateFlow()

    init {
        loadAmiibo()
    }

    fun onSortOrderChanged(order: SortOrder){
        _uiState.update { it.copy(sortOrder = order) }
        loadAmiibo()
    }

    fun onFavoriteClicked(amiiboId: String) = viewModelScope.launch {
        repository.toggleFavorite(amiiboId)
        loadAmiibo()
    }

    private fun loadAmiibo() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val amiibo = getSortedAmiibosUseCase(_uiState.value.sortOrder)
            android.util.Log.d("AmiiboVM", "loaded ${amiibo.size} items")
            _uiState.update { it.copy(isLoading = false, amiibo = amiibo) }
        } catch (ex: Exception){
            _uiState.update { it.copy(isLoading = false, errorMessage = "Ошибка загрузки Amiibo" + ex.message.toString()) }
        }
    }

    // здесь для теста
    fun insertNote(amiiboId: String, text: String, rating: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.AddNote(amiiboId, text, rating)
        }
    }
}