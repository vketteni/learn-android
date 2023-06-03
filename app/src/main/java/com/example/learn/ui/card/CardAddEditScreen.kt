package com.example.learn.ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import com.example.learn.ui.deck.DeckInputForm
import com.example.learn.ui.deck.DeckUiState

@Composable
fun CardAddEditScreen(
    @StringRes topBarTitle: Int,
    onNavigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    onUpdateCard: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            LearnTopBar(title = stringResource(id = topBarTitle), canNavigateBack = true, navigateUp = onNavigateUp)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onUpdateCard() }) {
            }
        }
    ) { innerPadding ->

        CardAddEditContent(
            onNavigateBack = onNavigateBack,
            cardUiState = viewModel.cardUiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = viewModel::saveCard,
            modifier = modifier
                .padding(innerPadding),
        )
    }
}

@Composable
fun CardAddEditContent(
    onNavigateBack: () -> Unit,
    cardUiState: CardUiState,
    onValueChange: (CardUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp), // Add padding to the Column
        verticalArrangement = Arrangement.spacedBy(32.dp),

        ) {
        CardAddEditForm(
            cardUiState = cardUiState,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp) // Add padding to the DeckInputForm
        )
        Button(
            onClick = {
                onSaveClick()
                onNavigateBack()
            },
            enabled = cardUiState.actionEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}

@Composable
fun CardAddEditForm(
    cardUiState: CardUiState,
    onValueChange: (CardUiState) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
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
            value = cardUiState.frontContent,
            onValueChange = { onValueChange(cardUiState.copy(frontContent = it)) },
            label = { Text(stringResource(R.string.card_front_content_textfield_title)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray, // Set the outline color for focused state to gray
                unfocusedBorderColor = MaterialTheme.colors.onSurface,
            )
        )
        OutlinedTextField(
            value = cardUiState.backContent,
            onValueChange = { onValueChange(cardUiState.copy(backContent = it)) },
            label = { Text(stringResource(R.string.card_back_content_title)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray, // Set the outline color for focused state to gray
                unfocusedBorderColor = MaterialTheme.colors.onSurface,
            )
        )


    }
}