package com.example.codechallenge.network

import com.example.codechallenge.data.model.Book
import com.example.codechallenge.data.response.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BookService {


    @GET("books")
    suspend fun getListOfBooks(): List<Book>

    @GET("books/{id}")
    suspend fun getBook(@Path("id") id: String): Book

    @POST("books")
    suspend fun addBook(@Body book: Book): Response

    @PATCH("books/{id}")
    suspend fun updateBook(@Path("id") id: String, @Body book: Book): Response

    @DELETE("books/{id}")
    suspend fun deleteBook(@Path("id") id: String): Response


}