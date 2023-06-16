package com.shahott.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        viewModel.books.observe(this) { domainBooks->
           bookAdapter.submitList(domainBooks)
        }
    }

}