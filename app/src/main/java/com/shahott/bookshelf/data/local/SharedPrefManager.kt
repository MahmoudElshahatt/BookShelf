package com.shahott.bookshelf.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private var mSharedPref: SharedPreferences? =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREFERENCES_NAME = "sh_chat_app"
        const val LAST_FETCH = "LAST_FETCH"
    }

    fun read(key: String?, defValue: Long): Long {
        return mSharedPref!!.getLong(key, defValue)
    }

    fun read(key: String?, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun write(key: String?, value: Long) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putLong(key, value)
        prefsEditor.commit()
    }

    fun write(key: String?, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!).commit()
    }
}