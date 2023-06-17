package com.shahott.bookshelf.ui


import com.shahott.bookshelf.base.BaseCacheStrategy
import com.shahott.bookshelf.base.BaseRepository
import com.shahott.bookshelf.data.local.SharedPrefManager
import com.shahott.bookshelf.models.domain.DomainBooks
import com.shahott.bookshelf.models.local.LocalBook
import com.shahott.bookshelf.models.local.asDomainModel
import com.shahott.bookshelf.models.remote.Books
import com.shahott.bookshelf.models.remote.asLocalDBModel
import com.shahott.bookshelf.ui.local.BooksDao
import com.shahott.bookshelf.util.network.ResultWrapper
import com.shahott.bookshelf.util.network.safeApiCall
import javax.inject.Inject

class MainRepository @Inject constructor(private val localDb: BooksDao) : BaseRepository(),
    BaseCacheStrategy<Books, LocalBook, String> {

    suspend fun getBooks(category: String): ResultWrapper<List<DomainBooks>> {
        return safeApiCall {
            return@safeApiCall getData(extra = category).map {
                it.asDomainModel()
            }
        }
    }

    override suspend fun getFromCache(
        page: Int,
        pageSize: Int,
        extra: String
    ): List<LocalBook> {
        return localDb.getAllBooks()
    }

    override suspend fun clearCachedData(extra: String) {
        localDb.deleteBooks()
    }

    override suspend fun saveToCache(page: Int, pageSize: Int, data: List<LocalBook>) {
        localDb.insertBooks(data)
    }

    override fun mapFromRemoteToLocal(remoteData: Books): List<LocalBook> {
        return remoteData.items!!.map {
            it?.bookInfo!!.asLocalDBModel()
        }
    }

    override suspend fun fetchFromRemote(
        page: Int,
        pageSize: Int,
        extra: String
    ): Books {
        return remoteData.getBooks(extra)
    }

    override suspend fun getLastSaveTime(): Long =
        sharedPref.read(SharedPrefManager.LAST_FETCH, 0L)

    override suspend fun updateLastSaveTime(timeStamp: Long) {
        sharedPref.write(SharedPrefManager.LAST_FETCH, timeStamp)
    }

}