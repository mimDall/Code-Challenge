package com.example.codechallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.data.model.Book
import com.example.codechallenge.network.Resource
import com.example.codechallenge.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(val bookRepository: BookRepository) : ViewModel() {
    private val TAG = "BookViewModel"

    private val _bookListResponse : MutableLiveData<Resource<List<Book>>> = MutableLiveData()
    val bookListResponse : LiveData<Resource<List<Book>>>
        get() = _bookListResponse




    fun getBookList() = viewModelScope.launch {
        _bookListResponse.value = Resource.Loading
        _bookListResponse.value = bookRepository.getAllBooks()

    }








}