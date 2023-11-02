package com.example.learn.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
                            onNavigateUp()
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
            content = CardUiContent(
                uiState.contentFront,
                uiState.contentBack
            ),
            onValueChange = viewModel::updateUiState,
            modifier = modifier
                .padding(innerPadding),
        )
    }
}

@Composable
fun CardAddEditBody(
    content: CardUiContent,
    onValueChange: (CardUiContent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp), // Add padding to the Column
        verticalArrangement = Arrangement.Center
    ) {
        ContentForms(
            content = content,
            onValueChange = onValueChange,
        )
    }
}

@Composable
fun ContentForms(
    content: CardUiContent,
    onValueChange: (CardUiContent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LearnOutlinedTextField(
        title = R.string.card_add_edit_front_content_text_field_title,
        value = content.front,
        onValueChange = { onValueChange(content.copy(front = it)) },
        placeholder = null,
        singleLine = false,
        minLines = 10,
        maxLines = 10
    )
    LearnOutlinedTextField(
        title = R.string.card_add_edit_back_content_text_field_title,
        value = content.back,
        onValueChange = { onValueChange(content.copy(back = it)) },
        placeholder = null,
        singleLine = false,
        minLines = 10,
        maxLines = 10
    )
}