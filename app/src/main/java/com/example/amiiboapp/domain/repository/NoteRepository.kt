package com.example.amiiboapp.domain.repository

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Note

interface NoteRepository {
    suspend fun AddNote(note: Note)

    suspend fun DropNote(noteId: String)

    suspend fun updateCoverImage(noteId: String, sourceUri: String): String

    suspend fun addCoverImage(amiiboId: String, sourceUri: String): String

    suspend fun getAllNotes(): List<Note>


}