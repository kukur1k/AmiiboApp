package com.example.amiiboapp.data.repository

import com.example.amiiboapp.data.remote.AmiiboRemoteDataSource
import com.example.amiiboapp.data.local.AmiiboLocalDataSource
import com.example.amiiboapp.data.local.ImageStorage
import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.model.Release
import com.example.amiiboapp.domain.repository.AmiiboRepository
import com.example.amiiboapp.domain.repository.NoteRepository
import javax.inject.Inject
import okio.IOException

class NoteRepositoryImpl @Inject constructor(
    private val remote: AmiiboRemoteDataSource,
    private val local: AmiiboLocalDataSource,
    private val imageStorage: ImageStorage
): NoteRepository {


    override suspend fun AddNote(note: Note){
        local.insertAmiiboNotes(note)
    }

    override suspend fun updateCoverImage(noteId: String, sourceUri: String): String {
        val savedPath = imageStorage.saveImage(sourceUri, noteId)
        local.updateCoverImage(noteId, savedPath)
        return savedPath
    }

    override suspend fun addCoverImage(amiiboId: String, sourceUri: String): String {
        val savedPath = imageStorage.saveImageCreate(sourceUri, amiiboId)
        local.updateCoverImage(amiiboId, savedPath)
        return savedPath
    }

    override suspend fun getAllNotes(): List<Note> {
        return local.getAllNotes()
    }

    override suspend fun DropNote(noteId: String) {
        local.dropNote(noteId)
    }

}