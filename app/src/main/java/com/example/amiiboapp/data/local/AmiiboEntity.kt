package com.example.amiiboapp.data.local

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

@Entity(tableName = "amiibo")
data class AmiiboEntity(
    @PrimaryKey val id: String,
    val head: String,
    val tail: String,
    val name: String,
    val character: String,
    val gameSeries: String,
    val amiiboSeries: String,
    val type: String,
    val image: String,
    val isFavorite: Boolean,
    @Embedded(prefix = "release_") val release: ReleaseEmbedded?
)

data class ReleaseEmbedded(
    val australia: String?,
    val europe: String?,
    val japan: String?,
    val northAmerica: String?
)

@Entity(tableName = "note_amiibo",
    foreignKeys = [ForeignKey(
        entity = AmiiboEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("amiiboId"),
        onDelete = ForeignKey.CASCADE
    )])
data class NoteAmiiboEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amiiboId: String,
    val text: String,
    val ratingNote: Int
)