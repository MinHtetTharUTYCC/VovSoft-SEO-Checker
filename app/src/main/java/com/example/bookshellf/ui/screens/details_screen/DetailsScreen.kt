package com.example.bookshellf.ui.screens.details_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshellf.R
import com.example.bookshellf.model.Book
import com.example.bookshellf.model.ListPrice
import com.example.bookshellf.model.SaleInfo
import com.example.bookshellf.model.VolumeInfo
import com.example.bookshellf.ui.screens.components.ErrorScreen
import com.example.bookshellf.ui.screens.components.LoadingScreen
import com.example.bookshellf.ui.theme.BookshellfTheme

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel,
    retryAction: () -> Unit,
) {

    when (val uiStateDetails = viewModel.uiStateDetail.collectAsState().value) {
        DetailsUiState.Error -> {
            ErrorScreen(retryAction = retryAction)
        }
        DetailsUiState.Loading -> {
            Log.d("now:","loading..")
            LoadingScreen()
        }
        is DetailsUiState.Success -> {
            Log.d("now:","success..")
            BookDetails(uiStateDetails.bookItem)
        }
    }



}

@Composable
fun BookDetails(bookItem: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bookItem.volumeInfo.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bookItem.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (bookItem.volumeInfo.subtitle != null) {
                Text(
                    text = stringResource(id = R.string.book_subtitle, bookItem.volumeInfo.subtitle),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = stringResource(id = R.string.book_authors, bookItem.volumeInfo.allAuthors()),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Text(
                text = stringResource(id = R.string.book_price, bookItem.saleInfo.getPrice2),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Text(
                text = "Country: " + bookItem.saleInfo.country,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Text(
                text = "List Price: " + bookItem.getPrice(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            if (bookItem.volumeInfo != null)

            {
                Text(
                    text = "Description: " + bookItem.volumeInfo.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsScreenPreview() {
    BookshellfTheme {

        val volumeInfo = VolumeInfo(
            title = "A book",
            description = "Caniss ortum, tanquam bassus exemplar.",
            publishedDate = "11/11/2011",
            authors =  listOf("AAA","aaa"),
            publisher = "John Carter",
            subtitle = "Cunu litist",
            imageLinks = null,
        )
        val saleInfo = SaleInfo(
            country = "USA",
            isEbook = false,
            listPrice = ListPrice(amount = 2.22f, currency = "US Dollar")
        )

        val mockData =
            Book(
                id = "123",
                volumeInfo = volumeInfo,
                saleInfo = saleInfo,
                description = volumeInfo.description
            )
        BookDetails(bookItem = mockData)
    }
}

