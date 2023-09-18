package com.example.learn.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import com.example.learn.ui.deck.DeleteConfirmationDialog
import kotlinx.coroutines.launch

@Composable
fun CardDetailScreen(
    onNavigateCardEdit: () -> Unit,
    onNavigateUp: (deckId: String) -> Unit,
    onNavigateCardDetail: (cardId: String) -> Unit,
    onNavigateDeckDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LearnTopBar(
                title = R.string.card_detail_title,
                canNavigateBack = true,
                navigateUp = { onNavigateUp(viewModel.deckId) },
                actions = {
                    IconButton(onClick = { onNavigateCardEdit() }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { deleteConfirmationRequired = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                onNavigateCardDetail(
                                    viewModel.getIdByCardPosition(
                                        position =  if (uiState.cardPosition != 0) uiState.cardPosition - 1 else uiState.deckLength - 1
                                    )
                                )
                            }
                        } ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = { viewModel.switchSides() }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                onNavigateCardDetail(
                                    viewModel.getIdByCardPosition(
                                        position = if (uiState.cardPosition != uiState.deckLength - 1) uiState.cardPosition + 1 else 0
                                    )
                                )
                            }
                        } ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        CardDetailBody(
            content = if (uiState.displayFront) uiState.contentFront else uiState.contentBack,
            modifier = modifier
                .padding(innerPadding)
        )
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    viewModel.deleteCard()
                },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }
        LaunchedEffect(uiState.isDeleted) {
            if (uiState.isDeleted) {
                onNavigateDeckDetail()
            }
        }

    }
}

@Composable
fun CardDetailBody(
    content: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(
            value = content,
            onValueChange = {},
            modifier = modifier,
            readOnly = true,
            minLines = 10
        )
    }

}