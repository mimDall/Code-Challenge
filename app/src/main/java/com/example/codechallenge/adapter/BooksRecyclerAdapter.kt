package com.example.codechallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.data.model.Book
import com.example.codingchallenge.databinding.BookItemBinding

class BookListRecyclerAdapter(var onDeleteClick: ((Book) -> Unit)?=null,
                              var onUpdateClick: ((Book) -> Unit)?=null,
                                var onCardClick: ((String)-> Unit)?=null) : RecyclerView.Adapter<BookListRecyclerAdapter.BookListViewHolder>() {

    private lateinit var binding: BookItemBinding
    var items : List<Book> = ArrayList()



    fun updateData(bookList: List<Book>){
       items = bookList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  BookListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        val book : Book = items.get(position)
        book.let {
            holder.bindItem(it)
        }

    }


    inner class BookListViewHolder(private val binding : BookItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.bookEdit.setOnClickListener{
                onUpdateClick?.invoke(items.get(adapterPosition))
            }

            binding.bookDelete.setOnClickListener{
                onDeleteClick?.invoke(items.get(adapterPosition))

            }

            binding.rootCard.setOnClickListener {
                items.get(adapterPosition).id?.let { it1 -> onCardClick?.invoke(it1) }
            }
        }

        fun bindItem(book : Book){
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookGenre.text = book.genre
//            binding.imgBg.backgroundTintList = Color.parseColor("ff00ff")
            binding.imgLetter.text = book.title?.get(0)?.toUpperCase().toString()
        }


    }
}