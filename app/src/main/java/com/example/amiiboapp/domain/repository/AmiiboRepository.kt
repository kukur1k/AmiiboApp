package com.example.amiiboapp.domain.repository

import com.example.amiiboapp.domain.model.Amiibo

interface AmiiboRepository {
    suspend fun getAllAmiibo(): List<Amiibo>
    suspend fun toggleFavorite(amiiboId: String)
    suspend fun insertAmiibo(amiibo: Amiibo)
    suspend fun deleteAmiibo(amiiboId: String)
    suspend fun updateAmiibo(amiiboId: String)
}