package com.example.amiiboapp.data.local

import com.example.amiiboapp.domain.model.Amiibo
import kotlinx.coroutines.flow.first

class AmiiboLocalDataSource(private val dao: AmiiboDao) {
    suspend fun getCachedAmiibo(): List<Amiibo> = dao.amiiboAll().first().map { it.toDomain() }
    suspend fun hasCachedAmiibo(): Boolean = dao.count() > 0

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