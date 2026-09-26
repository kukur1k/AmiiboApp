package com.example.amiiboapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AmiiboResponseDto(
    val amiibo: List<AmiiboDto>
)

@Serializable
data class AmiiboDto(
    val head: String,
    val tail : String,
    val name: String,
    val character: String,
    val gameSeries: String,
    val amiiboSeries: String,
    val type: String,
    val image: String,
    @SerialName("release")
    val release: ReleaseDto?,
)

@Serializable
data class ReleaseDto(
    val au: String?,
    val eu: String?,
    val jp: String?,
    val na: String?
)