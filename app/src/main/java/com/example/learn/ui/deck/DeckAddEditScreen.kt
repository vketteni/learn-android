@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.learn.ui.deck

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme

import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider

@Composable
fun DeckAddEditScreen(
    navigateDeckOverview: () -> Unit,
    navigateUp: () -> Unit,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    viewModel: DeckAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            LearnTopBar(title = title, canNavigateBack = true, navigateUp = navigateUp)
        },
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        DeckEntryBody(
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
fun DeckEntryBody(
    deckUiState: DeckAddEditUiState,
    onDeckValueChange: (DeckAddEditUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // deck title input field
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp), // Add padding to the Column
        verticalArrangement = Arrangement.spacedBy(32.dp),

    ) {
        DeckInputForm(
            deckUiState = deckUiState,
            onValueChange = onDeckValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp) // Add padding to the DeckInputForm
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
    deckUiState: DeckAddEditUiState,
    modifier: Modifier = Modifier,
    onValueChange: (DeckAddEditUiState) -> Unit,
    enabled: Boolean = true
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Add padding to the Column
            .padding(vertical = 16.dp) // Add padding to the Column
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = deckUiState.title,
            onValueChange = { onValueChange(deckUiState.copy(title = it)) },
            label = { Text(stringResource(R.string.deck_title_req)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Gray, // Set the outline color for focused state to gray
                unfocusedBorderColor = MaterialTheme.colors.onSurface
            )
        )


    }
}
