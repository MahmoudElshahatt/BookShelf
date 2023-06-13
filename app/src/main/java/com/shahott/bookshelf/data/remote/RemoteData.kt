package com.shahott.bookshelf.data.remote

import com.shahott.bookshelf.models.Books
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteData {

    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String):Books

}