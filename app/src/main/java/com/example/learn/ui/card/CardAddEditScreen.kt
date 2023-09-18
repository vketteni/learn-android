package com.example.learn.ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider

@Composable
fun CardAddEditScreen(
    @StringRes title: Int,
    onNavigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        topBar = {
            LearnTopBar(title = title, canNavigateBack = true, navigateUp = onNavigateUp)
        },

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
        val textfieldColors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Gray,
            focusedBorderColor = Color.Gray, // Set the outline color for focused state to gray
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
        )


        OutlinedTextField(
            value = cardUiState.contentFront,
            onValueChange = { onValueChange(cardUiState.copy(contentFront = it)) },
            label = { Text(stringResource(R.string.card_front_content_textfield_title)) },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = enabled,
            minLines = 6,
            colors = textfieldColors,
            shape = CutCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 8.dp, bottomStart = 8.dp)
        )
        OutlinedTextField(
            value = cardUiState.contentBack,
            onValueChange = { onValueChange(cardUiState.copy(contentBack = it)) },
            label = { Text(stringResource(R.string.card_back_content_title)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            minLines = 6,
            colors = textfieldColors,
            shape = CutCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 8.dp, bottomStart = 8.dp)
        )


    }
}