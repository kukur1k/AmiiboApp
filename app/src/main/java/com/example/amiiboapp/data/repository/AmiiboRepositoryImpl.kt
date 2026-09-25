package com.example.amiiboapp.data.repository

import com.example.amiiboapp.data.remote.AmiiboRemoteDataSource
import com.example.amiiboapp.data.local.AmiiboLocalDataSource
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.repository.AmiiboRepository
import jakarta.inject.Inject
import okio.IOException

class AmiiboRepositoryImpl @Inject constructor(
    private val remote: AmiiboRemoteDataSource,
    private val local: AmiiboLocalDataSource
): AmiiboRepository {
    override suspend fun getAllAmiibo(): List<Amiibo> {
        return try{
            val remoteAmiibo = remote.fetchAmiibo().map { dto ->
                Amiibo(
                    id = dto.id.toString(),
                    name = dto.name,
                    character = dto.character,
                    gameSeries = dto.gameSeries,
                    amiiboSeries = dto.amiiboSeries,
                    type = dto.type,
                    imageUrl = dto.imageUrl,
                    release = dto.release,
                    isFavorite = false,
                    noteId = null
                )
            }
            local.cacheAmiibo(remoteAmiibo)
            local.getCachedAmiibo()
        } catch (ex: IOException){
            local.getCachedAmiibo()
        }
    }

    override suspend fun toggleFavorite(amiiboId: String) {
        val current = local.getCachedAmiibo().first {it.id == amiiboId}
        local.setFavorite(amiiboId, !current.isFavorite)
    }
}