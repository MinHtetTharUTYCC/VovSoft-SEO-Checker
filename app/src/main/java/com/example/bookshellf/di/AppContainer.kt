package com.example.bookshellf.di

import com.example.bookshellf.data.BookshelfRepository
import com.example.bookshellf.network.BookshelfApiService

interface AppContainer {
    val bookshelfApiService: BookshelfApiService
    val bookshelfRepository: BookshelfRepository
}