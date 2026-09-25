package com.example.amiiboapp.data.remote

import com.example.amiiboapp.domain.model.Release
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AmiiboResponseDto(
    val results: List<AmiiboDto>
)

@Serializable
data class AmiiboDto(
    val id: Int,
    val name: String,
    val character: String,
    val gameSeries: String,
    val amiiboSeries: String,
    val type: String,
    val imageUrl: String,
    @SerialName("release")
    val release: Release?,
)

@Serializable
data class Release(
    val au: String?,
    val eu: String?,
    val jp: String?,
    val na: String?
)