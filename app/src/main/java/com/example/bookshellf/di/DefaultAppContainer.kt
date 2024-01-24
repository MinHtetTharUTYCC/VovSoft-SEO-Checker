package com.example.bookshellf.di

import com.example.bookshellf.data.BookshelfRepository
import com.example.bookshellf.data.DefaultBookshelfRepository
import com.example.bookshellf.network.BookshelfApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DefaultAppContainer: AppContainer{
    override val bookshelfApiService: BookshelfApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BookshelfApiService.BASE_URL)
            .build()
            .create()
    }
    override val bookshelfRepository: BookshelfRepository by lazy {
        DefaultBookshelfRepository(bookshelfApiService)
    }


}