package com.example.learn.ui.deck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun DeckEntryScreen(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: DeckEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
                 LearnTopBar(title = stringResource(R.string.deck_entry_screen_title), canNavigateBack = canNavigateBack, navigateUp = navigateUp)
        },

    ) { innerPadding ->
        DeckEntryBody(
            deckUiState = viewModel.deckUiState,
            onDeckValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveDeck()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )

    }
}

@Composable
fun DeckEntryBody(
    deckUiState: DeckUiState,
    onDeckValueChange: (DeckUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // deck title input field
    Column(
        modifier = modifier
            .fillMaxWidth()
            //.padding(16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ){
        DeckInputForm(
            deckUiState = deckUiState,
            onValueChange = onDeckValueChange
        )
        Button(
            onClick = onSaveClick,
            enabled = deckUiState.actionEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}

@Composable
fun DeckInputForm(
    deckUiState: DeckUiState,
    modifier: Modifier = Modifier,
    onValueChange: (DeckUiState) -> Unit,
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = deckUiState.title,
            onValueChange = { onValueChange(deckUiState.copy(title = it)) },
            label = { Text(stringResource(R.string.deck_title_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
