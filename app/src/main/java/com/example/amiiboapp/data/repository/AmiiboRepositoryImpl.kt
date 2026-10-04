package com.example.amiiboapp.data.repository

import android.R
import com.example.amiiboapp.data.remote.AmiiboRemoteDataSource
import com.example.amiiboapp.data.local.AmiiboLocalDataSource
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.model.Release
import com.example.amiiboapp.domain.repository.AmiiboRepository
import javax.inject.Inject
import okio.IOException

class AmiiboRepositoryImpl @Inject constructor(
    private val remote: AmiiboRemoteDataSource,
    private val local: AmiiboLocalDataSource
): AmiiboRepository {
    override suspend fun getAllAmiibo(): List<Amiibo> {
        return try{
            val remoteAmiibo = remote.fetchAmiibo().map { dto ->
                Amiibo(
                    id = dto.head + dto.tail,
                    head = dto.head,
                    tail = dto.tail,
                    name = dto.name,
                    character = dto.character,
                    gameSeries = dto.gameSeries,
                    amiiboSeries = dto.amiiboSeries,
                    type = dto.type,
                    imageUrl = dto.image,
                    release = dto.release?.let { Release(
                        au = it.au,
                        eu = it.eu,
                        jp = it.jp,
                        na = it.na
                    ) },
                    isFavorite = false,
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

    override suspend fun AddNote(amiiboId: String, text: String, rating: Int){
        local.insertAmiiboNotes(amiiboId, text, rating)
    }

    override suspend fun getAllNotes(): List<Note> {
        return local.getAllNotes()
    }
}