package com.example.bookshellf.ui.screens.menu_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshellf.R
import com.example.bookshellf.ui.theme.BookshellfTheme

@Composable
fun MenuScreen(
    onSearchClick:() -> Unit,
    onFavClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.padding_large)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onSearchClick ) {
            Text(text = stringResource(id = R.string.btn_search))
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        Button(onClick = onFavClick) {
            Text(text = stringResource(id = R.string.btn_favorites))
        }

    }
}

@Preview(showBackground =  true)
@Composable
fun MenuScreenPreview() {
    BookshellfTheme {
        MenuScreen(onSearchClick = { }, onFavClick = {  })
    }
}