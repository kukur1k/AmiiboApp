package com.example.amiiboapp.data.local

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Note
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AmiiboLocalDataSource @Inject constructor(private val dao: AmiiboDao) {
    suspend fun getCachedAmiibo(): List<Amiibo> = dao.amiiboAll().first().map { it.toDomain() }
    suspend fun hasCachedAmiibo(): Boolean = dao.count() > 0

    suspend fun getAllNotes(): List<Note> = dao.notesAll().first().map { it.toDomain() }

    suspend fun updateCoverImage(noteId: String, savedPath: String) = dao.updateCoverImage(noteId, savedPath)
    suspend fun insertAmiiboNotes(amiiboId: String, text: String, rating: Int, imagePath: String?){
        dao.InsertNote(amiiboId, text, rating, imagePath)
    }

    suspend fun cacheAmiibo(amiibo: List<Amiibo>){
        val exFavotiteIds = dao.amiiboAll().first()
            .filter { it.isFavorite}
            .map { it.id }
            .toSet()

        val enities = amiibo.map { amiibo ->
            amiibo.toEntity().copy(isFavorite = amiibo.id in exFavotiteIds)
        }

        dao.insertAll(enities)
    }

    suspend fun setFavorite(amiiboId: String, isFavorite: Boolean) = dao.setFavorite(amiiboId, isFavorite)
}