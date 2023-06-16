package com.shahott.bookshelf.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shahott.bookshelf.R
import com.shahott.bookshelf.databinding.BookItemBinding
import com.shahott.bookshelf.models.domain.DomainBooks

class BooksAdapter() :
    ListAdapter<DomainBooks, BooksAdapter.BookItemViewHolder>(ListAdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val binding = BookItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookItemViewHolder(binding)
    }

    inner class BookItemViewHolder(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: DomainBooks) {
            Glide.with(itemView.context)
                .load(book.imageUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_book)
                .into(binding.imgBook)

            binding.txtBookName.text = book.bookName
            binding.txtDescription.text = book.shortDescription
            binding.txtLanguageOfBook.text = book.bookLanguage
            binding.txtPageCount.text = book.pageCount.toString()
            binding.txtAuthor.text = book.author
        }
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ListAdapterDiffUtil : DiffUtil.ItemCallback<DomainBooks>() {

        override fun areItemsTheSame(oldItem: DomainBooks, newItem: DomainBooks): Boolean {
            return oldItem.bookName == newItem.bookName
        }

        override fun areContentsTheSame(oldItem: DomainBooks, newItem: DomainBooks): Boolean {
            return oldItem == newItem
        }
    }

}

