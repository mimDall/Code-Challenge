package com.example.codechallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.data.model.Book
import com.example.codingchallenge.databinding.SearchedBookItemBinding

class SearchedBookAdapter(var onClicked : ((String) -> Unit)?= null) : RecyclerView.Adapter<SearchedBookAdapter.SearchedBookViewHolder>() {

    private lateinit var binding : SearchedBookItemBinding
    var searchedBook : List<Book> = ArrayList()



    fun updateSearchedBook (bookList: List<Book>){
        searchedBook = bookList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedBookViewHolder {
        binding = SearchedBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedBookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchedBook.size
    }

    override fun onBindViewHolder(holder: SearchedBookViewHolder, position: Int) {
        val book : Book = searchedBook.get(position)
        book.let {
            holder.bindItem(it)
        }
    }


    inner class SearchedBookViewHolder(private val binding : SearchedBookItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.card.setOnClickListener{
                searchedBook.get(adapterPosition).id?.let { it1 -> onClicked?.invoke(it1) }
            }
        }

        fun bindItem(book : Book){
            binding.searchedBookTitle.text = book.title
            binding.searchedBookAuthor.text = book.author
            binding.searchedImgLetter.text = book.title?.get(0)?.toUpperCase().toString()

        }

    }
}