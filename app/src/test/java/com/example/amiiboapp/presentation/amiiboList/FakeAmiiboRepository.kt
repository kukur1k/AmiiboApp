package com.example.amiiboapp.presentation.amiiboList

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.repository.AmiiboRepository
import java.io.IOException

class FakeAmiiboRepository: AmiiboRepository {
    private var amiibo = listOf<Amiibo>()
    var shouldThrow = false

    fun setAmiibo(list: List<Amiibo>) {
        amiibo = list
    }

    override suspend fun getAllAmiibo(): List<Amiibo> {
        if (shouldThrow) throw IOException("fake failure")
        return amiibo
    }

    override suspend fun toggleFavorite(amiiboId: String) {
        amiibo = amiibo.map {
            if (it.id == amiiboId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    override suspend fun getAmiiboById(amiiboId: String): Amiibo? {
        if (shouldThrow) throw IOException("fake failure")
        return amiibo.first { it.id == amiiboId }
    }
}