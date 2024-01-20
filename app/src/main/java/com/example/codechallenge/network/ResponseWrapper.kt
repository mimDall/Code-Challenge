package com.example.codechallenge.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class ResponseWrapper {

    suspend fun<T> handleApiResponse (
        api : suspend () -> T
    ) :Resource<T> {

        return withContext(Dispatchers.IO){
            try {
                Resource.Success(api.invoke())
            }catch (exception : Exception){
                when(exception){
                    is HttpException -> {
                        Resource.Failure(false, exception.code(), exception.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }


    }
}