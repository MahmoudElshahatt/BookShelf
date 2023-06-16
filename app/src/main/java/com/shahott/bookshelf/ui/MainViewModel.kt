package com.shahott.bookshelf.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahott.bookshelf.models.domain.DomainBooks
import com.shahott.bookshelf.models.remote.Books
import com.shahott.bookshelf.models.remote.asDomainModel
import com.shahott.bookshelf.util.network.ErrorResponse
import com.shahott.bookshelf.util.network.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SOFTWARE_CATEGORY = "software"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    /**
     * UI states
     */
    private val _books = MutableLiveData<List<DomainBooks?>?>()
    val books: LiveData<List<DomainBooks?>?> = _books

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _generalError = MutableLiveData<String>()
    val generalError: LiveData<String> = _generalError

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean> = _networkError

    private var _job: Job? = null

    init {
        getBooks(SOFTWARE_CATEGORY)
    }

    private fun getBooks(category: String) {
        _job = viewModelScope.launch {
            _isLoading.value = true
            val result = mainRepository.getBooks(category)
            when (result) {
                is ResultWrapper.GenericError -> showGeneralError(result.error)
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.Success -> showSuccess(result.value)
            }
            _isLoading.postValue(false)
        }
    }

    private fun showNetworkError() {
        _networkError.value = true
    }

    private fun showGeneralError(error: ErrorResponse?) {
        _generalError.value = error?.message ?: ""
    }

    private fun showSuccess(booksRes: Books) {
        _books.value = booksRes.items?.map {
            it?.bookInfo?.asDomainModel()
        }
        Log.e("ViewModel", _books.value.toString())
    }


}