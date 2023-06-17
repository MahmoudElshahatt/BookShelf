package com.shahott.bookshelf.ui.di

import com.shahott.bookshelf.data.local.room.BooksDatabase
import com.shahott.bookshelf.ui.local.BooksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class BooksModule {

    @Provides
    @ViewModelScoped
    fun provideCirclesDao(roomDatabase: BooksDatabase): BooksDao {
        return roomDatabase.booksDao()
    }
}