package com.example.amiiboapp.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [
    AmiiboEntity::class,
    NoteAmiiboEntity::class], version = 1)
abstract class AmiiboDatabase : RoomDatabase() {
    abstract fun amiiboDao(): AmiiboDao
}