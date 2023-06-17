package com.shahott.bookshelf.base

import android.util.Log


interface BaseCacheStrategy<Remote, Local, Extra> {

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_SIZE = 20
        private const val DELAY_TIME = 3 * 60 * 60 * 1000L // 3 hours
    }

    suspend fun getFromCache(page: Int, pageSize: Int, extra: Extra): List<Local>

    suspend fun clearCachedData(extra: Extra)

    suspend fun saveToCache(page: Int, pageSize: Int, data: List<Local>)

    fun mapFromRemoteToLocal(remoteData: Remote): List<Local>

    suspend fun fetchFromRemote(page: Int, pageSize: Int, extra: Extra): Remote

    suspend fun getLastSaveTime(): Long

    suspend fun updateLastSaveTime(timeStamp: Long)

    suspend fun forceToRefresh(): Boolean = false


    suspend fun getData(
        page: Int = FIRST_PAGE,
        pageSize: Int = PAGE_SIZE,
        delayTimeToRefreshInMilli: Long = DELAY_TIME,
        extra: Extra,
    ): List<Local> {
        val cachedData = getFromCache(page, pageSize, extra)
        val currentTime = System.currentTimeMillis()
        val lastSaveTime = getLastSaveTime()

        return if (cachedData.isEmpty()
            || currentTime - lastSaveTime >= delayTimeToRefreshInMilli
            || forceToRefresh()
        ) {
            if (page == 1) {
                clearCachedData(extra)
            }
            val remoteData = fetchFromRemote(page, pageSize, extra)
            saveToCache(page, pageSize, mapFromRemoteToLocal(remoteData))
            val newCachedData = getFromCache(page, pageSize, extra)
            if (newCachedData.isNotEmpty()) {
                updateLastSaveTime(System.currentTimeMillis())
            }
            newCachedData
        } else {
            cachedData
        }

    }
}