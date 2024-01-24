package com.example.bookshellf.ui.screens.details_screen

import com.example.bookshellf.model.Book

sealed interface DetailsUiState{
    object Loading: DetailsUiState
    object Error: DetailsUiState
    data class Success(val bookItem: Book): DetailsUiState
}