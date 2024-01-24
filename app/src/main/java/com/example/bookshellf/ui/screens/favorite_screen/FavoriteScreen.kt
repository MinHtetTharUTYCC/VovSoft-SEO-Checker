package com.example.bookshellf.ui.screens.favorite_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bookshellf.R
import com.example.bookshellf.ui.screens.components.ErrorScreen
import com.example.bookshellf.ui.screens.components.LoadingScreen
import com.example.bookshellf.ui.screens.details_screen.DetailsUiState
import com.example.bookshellf.ui.screens.query_screen.GridList
import com.example.bookshellf.ui.screens.query_screen.QueryUiState
import com.example.bookshellf.ui.screens.query_screen.QueryViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: QueryViewModel,
    bookshelfUiState: QueryUiState,
    retryAction: () -> Unit,

    ) {
    Column {
        if (!viewModel.favoriteBooks.isEmpty()) {
            when (bookshelfUiState) {
                QueryUiState.Error -> ErrorScreen(retryAction =  retryAction, modifier = modifier)
                QueryUiState.Loading -> LoadingScreen(modifier)
                is QueryUiState.Success -> GridList(
                    viewModel = viewModel,
                    bookshelfList = bookshelfUiState.bookshelfList,
                    onDetailClick = {}
                )
            }
        }
        else {
            Box(modifier = modifier.fillMaxSize(),contentAlignment = Alignment.Center){
                Text(text = stringResource(R.string.no_favorite_books))

            }
        }

    }

}