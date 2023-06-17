package com.shahott.bookshelf.models.database

data class LocalBook(
    val bookName: String,
    val desc: String,
    val bookLanguage: String,
    val pageCount: Int,
    val author: String,
    val imageUrl: String
)
