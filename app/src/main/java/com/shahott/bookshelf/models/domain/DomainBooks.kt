package com.shahott.bookshelf.models.domain

import com.shahott.bookshelf.util.smartTruncate

data class DomainBooks(
    val bookName: String,
    val desc: String,
    val bookLanguage: String,
    val pageCount: Int,
    val author: String,
    val imageUrl: String,

    ) {
    val shortDescription: String
        get() = desc.smartTruncate(45)
}