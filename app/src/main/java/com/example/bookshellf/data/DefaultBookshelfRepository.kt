package com.example.bookshellf.data

import com.example.bookshellf.model.Book
import com.example.bookshellf.network.BookshelfApiService


class DefaultBookshelfRepository (private val bookshelfApiService: BookshelfApiService): BookshelfRepository{
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val result = bookshelfApiService.getBooks(query)

            if (result.isSuccessful) {
                result.body()?.items ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            val result = bookshelfApiService.getBook(id)

            if (result.isSuccessful) {
                result.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}