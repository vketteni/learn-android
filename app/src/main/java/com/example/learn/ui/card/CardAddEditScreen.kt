package com.example.learn.ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnBottomAppBar
import com.example.learn.LearnOutlinedTextField

import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun CardAddEditScreen(
    @StringRes title: Int,
    onNavigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardAddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(

        bottomBar = {
            LearnBottomAppBar {
                IconButton(onClick = {
                    coroutineScope.launch {
                        onNavigateUp()
                    }
                } ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveCard()
                        }
                    },
                    enabled = uiState.actionEnabled) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint =
                        if (uiState.actionEnabled) androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                        else androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
        }
    ) { innerPadding ->

        CardAddEditBody(
            onNavigateBack = onNavigateBack,
            contentFront = uiState.contentFront,
            contentBack = uiState.contentBack,
            onValueChange = viewModel::updateUiState,
            onSaveClick = viewModel::saveCard,
            modifier = modifier
                .padding(innerPadding),
        )
    }
}

@Composable
fun CardAddEditBody(
    onNavigateBack: () -> Unit,
    contentFront: String,
    contentBack: String,
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
        CardAddEditForms(
            cardContentFront = contentFront,
            cardContentBack = contentBack,
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
fun CardAddEditForms(
    cardContentFront: String,
    cardContentBack: String,
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

        LearnOutlinedTextField(
            value = cardContentFront,
            onValueChange = { onValueChange(cardUiState.copy(contentFront = it)) },
            placeholder = stringResource(R.string.card_front_content_textfield_title)
        )
        LearnOutlinedTextField(
            value = cardContentBack,
            onValueChange = { onValueChange(cardUiState.copy(contentBack = it)) },
            placeholder = stringResource(R.string.card_back_content_title)
        )
    }
}