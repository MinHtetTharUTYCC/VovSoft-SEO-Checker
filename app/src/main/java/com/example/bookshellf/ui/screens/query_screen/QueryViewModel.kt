package com.example.bookshellf.ui.screens.query_screen

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshellf.BookshelfApplication
import com.example.bookshellf.data.BookshelfRepository
import com.example.bookshellf.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class QueryViewModel(
    private val bookshelfRepository: BookshelfRepository
): ViewModel() {   //repo

    private val _uiState = MutableStateFlow<QueryUiState>(QueryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiStateSearch = MutableStateFlow(SearchUiState())
    val uiStateSearch = _uiStateSearch.asStateFlow()

    var selectedBookId by mutableStateOf("")

    var favoriteBooks: MutableList<Book> by mutableStateOf(mutableListOf<Book>())
        private set

    var favoritesUiState: QueryUiState by mutableStateOf(QueryUiState.Loading)

    fun isBookFavorite(book: Book): Boolean{
        return !favoriteBooks.filter { x-> x.id == book.id }.isEmpty()
    }

    fun addFav(book: Book) {
        if (!isBookFavorite(book)) {
            favoriteBooks.add(book)
            updateFavs()
        }
    }
    fun removeFav(book: Book) {
        favoriteBooks.removeIf { it.id == book.id }
        updateFavs()
    }


    private fun updateFavs() {
        viewModelScope.launch {
            favoritesUiState = QueryUiState.Loading
            favoritesUiState = QueryUiState.Success(favoriteBooks)
        }
    }

    fun updateQuery(query: String) {
        _uiStateSearch.update { currentState->
            currentState.copy(
                query = query
            )
        }
    }
    fun updateSearchStarted(searchStarted: Boolean) {
        _uiStateSearch.update { currentState->
            currentState.copy(
                searchStarted = searchStarted
            )
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getBooks(query: String = "") {
        updateSearchStarted(true)
        viewModelScope.launch {
            _uiState.value = QueryUiState.Loading

            _uiState.value = try {

                val books = bookshelfRepository.getBooks(query)
                if (books == null) {
                    QueryUiState.Error
                }
                else if (books.isEmpty()) {
                    QueryUiState.Success(emptyList())
                }
                else QueryUiState.Success(books)

            } catch (e: IOException) {
                QueryUiState.Error

            } catch (e: HttpException) {
                QueryUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository  = application.container.bookshelfRepository
                QueryViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}