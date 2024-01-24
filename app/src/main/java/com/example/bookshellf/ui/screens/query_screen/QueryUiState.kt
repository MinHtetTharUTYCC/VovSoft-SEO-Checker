package com.example.bookshellf.ui.screens.query_screen

import com.example.bookshellf.model.Book

sealed interface QueryUiState{
    data class Success(val bookshelfList: List<Book>): QueryUiState
    object Error: QueryUiState
    object Loading: QueryUiState
}