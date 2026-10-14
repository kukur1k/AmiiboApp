package com.example.amiiboapp.presentation.amiiboList

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.usecase.SortOrder

data class AmiiboListUiState(
    val amiibo: List<Amiibo> = emptyList(),
        val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val sortOrder: SortOrder = SortOrder.NAME
)