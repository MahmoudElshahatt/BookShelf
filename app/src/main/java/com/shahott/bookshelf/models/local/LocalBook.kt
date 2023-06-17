package com.shahott.bookshelf.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shahott.bookshelf.models.domain.DomainBooks

@Entity(tableName = "Books")
data class LocalBook(
    val bookName: String,
    val desc: String,
    val bookLanguage: String,
    val pageCount: Int,
    val author: String,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
/**
 * Convert Database cached data to domain objects
 */
fun LocalBook.asDomainModel() = DomainBooks(
    bookName = bookName,
    desc = desc,
    bookLanguage = bookLanguage,
    pageCount = pageCount,
    author = author,
    imageUrl = imageUrl
)

