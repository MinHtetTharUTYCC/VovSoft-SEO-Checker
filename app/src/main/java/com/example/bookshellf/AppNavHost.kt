package com.example.bookshellf

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookshellf.ui.screens.details_screen.DetailsScreen
import com.example.bookshellf.ui.screens.details_screen.DetailsViewModel
import com.example.bookshellf.ui.screens.favorite_screen.FavoriteScreen
import com.example.bookshellf.ui.screens.menu_screen.MenuScreen
import com.example.bookshellf.ui.screens.query_screen.QueryScreen
import com.example.bookshellf.ui.screens.query_screen.QueryViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookshelfNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val viewModel: QueryViewModel = viewModel(factory = QueryViewModel.Factory)


    NavHost(
        navController = navController,
        startDestination = AppDestinations.MenuScreen.name,
        modifier = modifier
    ) {
        composable(route = AppDestinations.MenuScreen.name) {
            MenuScreen(
                onSearchClick = { navController.navigate(AppDestinations.QueryScreen.name) },
                onFavClick = { navController.navigate(AppDestinations.FavoriteScreen.name) }
            )
        }
        composable(route = AppDestinations.QueryScreen.name) {
            QueryScreen(
                viewModel = viewModel,
                retryAction = { viewModel.getBooks() },
                onDetailClick = {
                viewModel.selectedBookId = it.id
                navController.navigate(AppDestinations.DetailScreen.name)
                })
        }
        composable(route = AppDestinations.FavoriteScreen.name) {
            FavoriteScreen(
                viewModel = viewModel,
                bookshelfUiState = viewModel.favoritesUiState,
                retryAction = { viewModel.getBooks() }
            )
        }
        composable(route = AppDestinations.DetailScreen.name) {
            val detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.Factory)
            detailsViewModel.getBook(viewModel.selectedBookId)
            DetailsScreen(viewModel = detailsViewModel, retryAction = { detailsViewModel.getBook(viewModel.selectedBookId)})
        }
    }

}