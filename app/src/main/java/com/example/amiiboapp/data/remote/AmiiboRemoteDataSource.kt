package com.example.amiiboapp.data.remote

import com.example.amiiboapp.data.remote.dto.AmiiboApi
import com.example.amiiboapp.data.remote.AmiiboDto
import javax.inject.Inject

class AmiiboRemoteDataSource @Inject constructor(
    private val api: AmiiboApi
) {
    suspend fun fetchAmiibo(): List<AmiiboDto> = api.getAmiibo().amiibo
}