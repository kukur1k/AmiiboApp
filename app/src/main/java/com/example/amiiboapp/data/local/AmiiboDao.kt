package com.example.amiiboapp.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.amiiboapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface AmiiboDao {
    @Query("SELECT * FROM amiibo")
    fun amiiboAll(): Flow<List<AmiiboEntity>>

    @Query("SELECT COUNT(*) FROM amiibo")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(amiibo: List<AmiiboEntity>)

    @Query("UPDATE amiibo SET isFavorite = :isFavorite WHERE id = :amiiboId")
    suspend fun setFavorite(amiiboId: String, isFavorite: Boolean)

    @Query("INSERT INTO note_amiibo(text, ratingNote, amiiboId, coverImagePath) values(:text, :ratingNote, :amiiboId, :imagePath)")
    suspend fun InsertNote(amiiboId: String, text: String, ratingNote: Int, imagePath: String?)

    @Query("SELECT * FROM note_amiibo")
    fun notesAll(): Flow<List<NoteAmiiboEntity>>

    @Query("UPDATE note_amiibo SET coverImagePath = :path WHERE id = :noteId")
    suspend fun updateCoverImage(noteId: String, path: String)
}