@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.learn.ui.deck

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnBottomAppBar
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun DeckAddEditScreen(
    navigateDeckOverview: () -> Unit,
    navigateUp: () -> Unit,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    viewModel: DeckAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {},
        bottomBar = {
            LearnBottomAppBar {
                IconButton(onClick = {
                    coroutineScope.launch {
                        navigateUp()
                    }
                } ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveDeck()
                        }
                    },
                    enabled = uiState.actionEnabled) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint =
                        if (uiState.actionEnabled) colorScheme.onSurfaceVariant
                        else colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
        }
    ) { innerPadding ->
        DeckAddEditBody(
            deckUiState = uiState,
            onDeckValueChange = viewModel::updateUiState,
            onSaveClick = viewModel::saveDeck,
            modifier = modifier.padding(innerPadding)
        )
        LaunchedEffect(uiState.isSaved) {
            if (uiState.isSaved) {
                navigateDeckOverview()
            }
        }
    }
}

@Composable
fun DeckAddEditBody(
    deckUiState: DeckAddEditUiState,
    onDeckValueChange: (DeckAddEditUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // deck title input field
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 16.dp), // Add padding to the Column
        verticalArrangement = Arrangement.Center,

    ) {
        DeckInputForm(
            deckUiState = deckUiState,
            onValueChange = onDeckValueChange,
            modifier = Modifier
                .fillMaxWidth()
        )

    }
}

@Composable
fun DeckInputForm(
    deckUiState: DeckAddEditUiState,
    modifier: Modifier = Modifier,
    onValueChange: (DeckAddEditUiState) -> Unit,
    enabled: Boolean = true
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 16.dp) // Add padding to the Column
            .padding(vertical = 16.dp) // Add padding to the Column
        ,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = deckUiState.title,
            onValueChange = { onValueChange(deckUiState.copy(title = it)) },
            placeholder = { Text(text = stringResource(R.string.deck_title_req))},
            enabled = enabled,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldColors(
                cursorColor = Color.LightGray
            )
        )
    }
}
