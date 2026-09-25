package com.example.amiiboapp.data.local

import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

interface AmiiboDao {
    @Query("SELECT * FROM amiibo")
    fun amiiboAll(): Flow<List<AmiiboEntity>>

    @Query("SELECT COUNT(*) FROM amiibo")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(amiibo: List<AmiiboEntity>)

    @Query("UPDATE amiibo SET isFavorite = :isFavorite WHERE id = :amiiboId")
    suspend fun setFavorite(amiiboId: String, isFavorite: Boolean)
}