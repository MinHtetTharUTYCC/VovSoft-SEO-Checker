package com.example.bookshellf.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshellf.R
import com.example.bookshellf.ui.theme.BookshellfTheme

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.connection_error),
            contentScale = ContentScale.Fit
        )
        Text(text = stringResource(id = R.string.fail_to_load), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))

        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.btn_try_again))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    BookshellfTheme {
        ErrorScreen(retryAction = { })
    }
}