package com.example.amiiboapp.data.local

import com.example.amiiboapp.domain.model.Note

fun NoteAmiiboEntity.toDomain() = Note(
    id = id.toString(),
    text = text,
    ratingNote = ratingNote,
    amiiboId = amiiboId
)

fun Note.toEntity() = NoteAmiiboEntity(
    id = id.toLong(),
    amiiboId = amiiboId,
    text = text,
    ratingNote = ratingNote,
)