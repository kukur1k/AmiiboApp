package com.example.amiiboapp.data.local

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

@Entity(tableName = "amiibo",
    foreignKeys = [ForeignKey(
        entity = NoteAmiibo::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("noteId"),
        onDelete = ForeignKey.CASCADE
    )])
data class AmiiboEntity(
    @PrimaryKey val id: String,
    val name: String,
    val character: String,
    val gameSeries: String,
    val amiiboSeries: String,
    val type: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    @Embedded(prefix = "release_") val release: ReleaseEmbedded?,
    val noteId: String?
)

data class ReleaseEmbedded(
    val australia: String?,
    val europe: String?,
    val japan: String?,
    val northAmerica: String?
)

@Entity(tableName = "note_amiibo")
data class NoteAmiibo(
    @PrimaryKey val id: String,

)