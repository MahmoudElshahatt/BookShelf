package com.shahott.bookshelf.base

import com.shahott.bookshelf.data.local.SharedPrefManager
import com.shahott.bookshelf.data.remote.RemoteData
import javax.inject.Inject

open class BaseRepository() {

    @Inject
    lateinit var remoteData: RemoteData

    @Inject
    lateinit var sharedPref: SharedPrefManager


}
