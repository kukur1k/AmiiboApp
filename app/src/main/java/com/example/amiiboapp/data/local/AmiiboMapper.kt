package com.example.amiiboapp.data.local

import com.example.amiiboapp.domain.model.Amiibo
import com.example.amiiboapp.domain.model.Release

fun AmiiboEntity.toDomain() = Amiibo(
    id = id,
    name = name,
    gameSeries = gameSeries,
    amiiboSeries = amiiboSeries,
    character = character,
    type = type,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    release = release?.toDomain()
)

fun ReleaseEmbedded.toDomain(): Release = Release(
    au = australia,
    eu = europe,
    jp = japan,
    na = northAmerica
)

fun Amiibo.toEntity() = AmiiboEntity(
    id = id,
    name = name,
    gameSeries = gameSeries,
    amiiboSeries = amiiboSeries,
    character = character,
    type = type,
    imageUrl = imageUrl,
    isFavorite = isFavorite,
    release = release?.toEmbedded(),
    noteId = noteId
)

fun Release.toEmbedded(): ReleaseEmbedded = ReleaseEmbedded(
    australia = au,
    europe = eu,
    japan = jp,
    northAmerica = na
)