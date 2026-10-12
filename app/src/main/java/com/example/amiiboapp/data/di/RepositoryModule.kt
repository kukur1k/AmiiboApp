package com.example.amiiboapp.data.di

import com.example.amiiboapp.data.local.AndroidImageStorage
import com.example.amiiboapp.data.local.ImageStorage
import com.example.amiiboapp.data.repository.AmiiboRepositoryImpl
import com.example.amiiboapp.data.repository.NoteRepositoryImpl
import com.example.amiiboapp.domain.repository.AmiiboRepository
import com.example.amiiboapp.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAmiiboRepository(
        impl: AmiiboRepositoryImpl
    ): AmiiboRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        impl: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    abstract fun bindImageStorage(impl: AndroidImageStorage): ImageStorage
}