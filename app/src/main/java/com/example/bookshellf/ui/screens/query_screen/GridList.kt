package com.example.bookshellf.ui.screens.query_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshellf.R
import com.example.bookshellf.model.Book
import com.example.bookshellf.ui.screens.components.NothingFoundScreen

private const val TAG = "MHT"

@Composable
fun GridList(
    viewModel: QueryViewModel,
    bookshelfList: List<Book>,
    onDetailClick: (Book) -> Unit,
    modifier: Modifier =  Modifier
) {
    if (bookshelfList.isEmpty()) {
        NothingFoundScreen(modifier = modifier)

    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(24.dp)
        ){
            items(bookshelfList) { book->
                GridItem(
                    viewModel = viewModel,
                    book = book,
                    onDetailClick = onDetailClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridItem(
    viewModel: QueryViewModel,
    book: Book,
    onDetailClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var favorite by remember {
        mutableStateOf(false)
    }

    favorite = viewModel.isBookFavorite(book)
    Log.d(TAG,viewModel.favoriteBooks.size.toString())

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = {onDetailClick(book)}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .listener(
                        onError = {_,throwable->
                            Log.e("error: ",throwable.toString())
                        }
                    )
                    .build(),
                contentDescription = null,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(.6f)

            )

            Log.d("thumbnail: ",book.volumeInfo.imageLinks?.thumbnail.toString())

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                FavoriteButton(favorite = favorite, onFavClick = {
                    if (favorite) {
                        viewModel.removeFav(book)
                    } else {
                        viewModel.addFav(book)
                    }
                    favorite = !favorite
                })
                ExpandButton(expanded = expanded, onExpandClick = {
                    expanded = !expanded
                })
            }

            Text(
                text = stringResource(id = R.string.book_title,book.volumeInfo.title),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )

            if (expanded) {
                Column(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                ){
                    val subTitle = book.volumeInfo.subtitle
                    if (subTitle != null) {
                        Text(
                            text = stringResource(id = R.string.book_subtitle,book.volumeInfo.subtitle),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    val authors = book.volumeInfo.authors
                    Text(
                        text = if(authors != null) stringResource(id = R.string.book_authors,book.volumeInfo.authors) else "Anonymous",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(id = R.string.book_price,book.volumeInfo.title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    favorite: Boolean,
    onFavClick: ()-> Unit
) {
    IconButton(onClick =  onFavClick ) {
        Icon(
            imageVector = if (favorite) Icons.Default.Favorite else Icons.Outlined.Favorite,
            tint = if(favorite) Color.Red else Color.Gray,
            contentDescription = null
        )
    }
}


@Composable
fun ExpandButton(
    expanded: Boolean,
    onExpandClick: ()-> Unit
) {
    IconButton(onClick = onExpandClick ) {
        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null
        )
    }
}
