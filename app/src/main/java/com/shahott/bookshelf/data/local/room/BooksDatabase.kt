package com.shahott.bookshelf.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shahott.bookshelf.models.local.LocalBook
import com.shahott.bookshelf.ui.local.BooksDao

@Database(entities = [LocalBook::class], version = 1)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}