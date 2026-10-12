package com.example.amiiboapp.domain.usecase

import com.example.amiiboapp.domain.model.Note
import com.example.amiiboapp.domain.repository.NoteRepository
import java.io.IOException

class FakeNoteRepository: NoteRepository {
    private var notes = mutableListOf<Note>()
    var shouldThrow = false

    var lastCoverUpdate: Pair<String, String>? = null

    fun setNotes(list: List<Note>) {
        notes = list as MutableList<Note>
    }

    override suspend fun getAllNotes(): List<Note> {
        if (shouldThrow) throw IOException("fake failure")
        return notes
    }

    override suspend fun AddNote(note: Note) {
        if (shouldThrow) throw IOException("fake failure")
        notes += note
    }

    override suspend fun DropNote(noteId: String) {
        if (shouldThrow) throw IOException("fake failure")
        notes.removeAll { it.id == noteId }
    }

    override suspend fun addCoverImage(amiiboId: String, sourceUri: String): String {
        if (shouldThrow) throw IOException("fake image failure")
        lastCoverUpdate = amiiboId to sourceUri
        return "saved_${amiiboId}_${System.currentTimeMillis()}.jpg"
    }

    override suspend fun updateCoverImage(noteId: String, sourceUri: String): String {
        if (shouldThrow) throw IOException("fake image failure")
        lastCoverUpdate = noteId to sourceUri
        val path = "saved_${noteId}_${System.currentTimeMillis()}.jpg"
        val idx = notes.indexOfFirst { it.id == noteId }
        if(idx != 0){
            notes[idx] = notes[idx].copy(coverImagePath = path)
        }
        return path
    }
}