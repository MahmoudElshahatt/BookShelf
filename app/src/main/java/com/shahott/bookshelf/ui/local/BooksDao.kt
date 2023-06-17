package com.shahott.bookshelf.ui.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahott.bookshelf.models.local.LocalBook

@Dao
interface BooksDao {

    @Query("SELECT * FROM Books")
    fun getAllBooks(): List<LocalBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<LocalBook>)

    @Query("DELETE FROM Books")
    fun deleteBooks()

}