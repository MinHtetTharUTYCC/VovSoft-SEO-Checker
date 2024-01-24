package com.example.bookshellf.data

import com.example.bookshellf.model.Book

interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>?

    suspend fun getBook(id: String): Book?
}