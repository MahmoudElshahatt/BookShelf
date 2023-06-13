package com.shahott.bookshelf.ui

import com.shahott.bookshelf.base.BaseRepository
import com.shahott.bookshelf.models.Books
import com.shahott.bookshelf.util.network.ResultWrapper
import com.shahott.bookshelf.util.network.safeApiCall
import javax.inject.Inject

class MainRepository @Inject constructor():BaseRepository() {

    suspend fun getBooks(category: String): ResultWrapper<Books> {
        return safeApiCall {
            return@safeApiCall remoteData.getBooks(category)
        }
    }

}