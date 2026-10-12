package com.example.amiiboapp.domain.repository

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Note

interface AmiiboRepository {
    suspend fun getAllAmiibo(): List<Amiibo>

    suspend fun getAmiiboById(amiiboId: String): Amiibo?
    suspend fun toggleFavorite(amiiboId: String)



}