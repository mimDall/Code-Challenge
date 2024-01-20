package com.example.codechallenge.repository

import com.example.codechallenge.data.model.Book
import com.example.codechallenge.network.ResponseWrapper
import com.example.codechallenge.network.BookService
import javax.inject.Inject

class BookRepository @Inject constructor(val bookService: BookService) : ResponseWrapper() {

    suspend fun getAllBooks() = handleApiResponse { bookService.getListOfBooks() }

    suspend fun getBook(id: String) = handleApiResponse { bookService.getBook(id) }

    suspend fun addBook(book: Book) = handleApiResponse { bookService.addBook(book) }

    suspend fun updateBook(id: String, book: Book) =
        handleApiResponse { bookService.updateBook(id, book) }

    suspend fun deleteBook(id: String) = handleApiResponse { bookService.deleteBook(id) }
}