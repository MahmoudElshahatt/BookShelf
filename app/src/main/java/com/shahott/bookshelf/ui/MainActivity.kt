package com.shahott.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.shahott.bookshelf.R
import com.shahott.bookshelf.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val SOFTWARE_CATEGORY = "software"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getBooks(SOFTWARE_CATEGORY)

    }

}