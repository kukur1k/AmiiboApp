package com.example.amiiboapp.data.remote.dto

import com.example.amiiboapp.data.remote.AmiiboResponseDto
import retrofit2.http.GET

interface AmiiboApi {
    @GET("amiibo/")
    suspend fun getAmiibo(): AmiiboResponseDto
}