package com.shahott.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.shahott.bookshelf.R
import com.shahott.bookshelf.databinding.ActivityMainBinding
import com.shahott.bookshelf.ui.adapter.BooksAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val bookAdapter = BooksAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)
        setUpBookRecycler()
        observations()

    }

    private fun setUpBookRecycler() {
        binding.rvBooks.adapter = bookAdapter
    }

    private fun observations() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.rvBooks.isVisible = !isLoading
        }
        viewModel.books.observe(this) { domainBooks ->
            bookAdapter.submitList(domainBooks)
        }
        viewModel.networkError.observe(this) { noInternet ->
            showErrorState("No Network Connection")
        }
        viewModel.generalError.observe(this) { generalError ->
            showErrorState(generalError)
        }
    }

    private fun showErrorState(errorMessage: String) {
        binding.rvBooks.isVisible = false
        binding.txtErrorState.text = errorMessage
        binding.txtErrorState.isVisible = true
    }

}