package com.example.learn.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnBottomAppBar
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
            LearnBottomAppBar {
                IconButton(onClick = {
                    coroutineScope.launch {
                        val previousCard = viewModel.getAdjacentCardUiReference(forward = false)
                        onNavigateCardDetail(
                            previousCard.cardId
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
                        val nextCard = viewModel.getAdjacentCardUiReference(forward = true)
                        onNavigateCardDetail(
                           nextCard.cardId
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
        },
//        containerColor = Color.Yellow
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .padding(top = innerPadding.calculateTopPadding()),
        ) {
            CardDetailBody(
                content = if (uiState.displayFront) uiState.contentFront else uiState.contentBack,
                modifier = modifier
                    .fillMaxSize()
            )
        }

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
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxSize()
    ) {
        CardDetailCard(content = content)
    }

}

@Composable
fun CardDetailCard(
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
