package com.example.amiiboapp.data.di

import android.content.Context
import androidx.room3.Room
import com.example.amiiboapp.data.local.AmiiboDao
import com.example.amiiboapp.data.local.AmiiboDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AmiiboDatabase {
        return Room.databaseBuilder(
            context,
            AmiiboDatabase::class.java,
            "amiibo.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAmiiboDao(database: AmiiboDatabase): AmiiboDao = database.amiiboDao()
}