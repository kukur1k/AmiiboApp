package com.example.amiiboapp.domain.usecase

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.repository.AmiiboRepository
import javax.inject.Inject

class GetSortedAmiibosUseCase @Inject constructor(
    private val repository: AmiiboRepository
){
    suspend operator fun invoke(sortBy: SortOrder): List<Amiibo> {
        val amiibos = repository.getAllAmiibo()

        return when (sortBy) {
            SortOrder.CHARACTER -> amiibos.sortedBy{ it.character }
            SortOrder.GAMESERIES -> amiibos.sortedBy { it.gameSeries }
            SortOrder.AMIBOSERIES -> amiibos.sortedBy { it.amiiboSeries }
            SortOrder.NAME -> amiibos.sortedBy { it.name }
        }
    }
}

enum class SortOrder { CHARACTER, GAMESERIES, AMIBOSERIES, NAME }