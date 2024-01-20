package com.example.codechallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.data.model.Book
import com.example.codechallenge.data.response.Response
import com.example.codechallenge.network.Resource
import com.example.codechallenge.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookViewModel @Inject constructor(val bookRepository: BookRepository) : ViewModel() {

    private val _bookResponse : MutableLiveData<Resource<Book>> = MutableLiveData()
    val bookResponse : LiveData<Resource<Book>>
        get() = _bookResponse

    private val _deleteBookResponse : MutableLiveData<Resource<Response>> = MutableLiveData()
    val deleteBookResponse : LiveData<Resource<Response>>
        get() = _deleteBookResponse

    private val _addBookResponse : MutableLiveData<Resource<Response>> = MutableLiveData()
    val addBookResponse : LiveData<Resource<Response>>
        get() = _addBookResponse

    private val _updateBookResponse : MutableLiveData<Resource<Response>> = MutableLiveData()
    val updateBookResponse : LiveData<Resource<Response>>
        get() = _updateBookResponse



    fun getBook(id:String) = viewModelScope.launch {
        _bookResponse.value = Resource.Loading
        _bookResponse.value = bookRepository.getBook(id)
    }


    fun deleteBook(id:String) = viewModelScope.launch {
        _deleteBookResponse.value = Resource.Loading
        _deleteBookResponse.value = bookRepository.deleteBook(id)

    }

    fun addBook(book: Book) = viewModelScope.launch {
        _addBookResponse.value = Resource.Loading
        _addBookResponse.value = bookRepository.addBook(book)

    }

    fun updateBook(id:String, book: Book) = viewModelScope.launch {
        _updateBookResponse.value = Resource.Loading
        _updateBookResponse.value = bookRepository.updateBook(id, book)

    }
}