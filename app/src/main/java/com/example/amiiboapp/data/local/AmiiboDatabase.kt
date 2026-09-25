package com.example.amiiboapp.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [AmiiboEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun amiiboDao(): AmiiboDao
}