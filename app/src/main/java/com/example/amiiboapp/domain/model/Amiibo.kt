package com.example.amiiboapp.domain.model

data class Amiibo(
    val id: String,
    val head: String,
    val tail: String,
    val name: String,
    val character: String,
    val gameSeries: String,
    val amiiboSeries: String,
    val type: String,
    val imageUrl: String,
    val release: Release?,
    val isFavorite: Boolean = false
)

data class Release(
    val au: String?,
    val eu: String?,
    val jp: String?,
    val na: String?
)

data class Note(
    val id: String,
    val amiiboId: String,
    val text: String,
    val ratingNote: Int
)