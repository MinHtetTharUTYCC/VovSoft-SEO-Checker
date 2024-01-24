package com.example.bookshellf.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.bookshellf.AppDestinations
import com.example.bookshellf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentScreen: AppDestinations,
    canNavigateBack: Boolean,
    onNavigateUpClick: () -> Unit,
) {
    TopAppBar(title = { Text(text = currentScreen.title) }, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick =  onNavigateUpClick ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.btn_try_again)
                )
            }
        }
    })
}