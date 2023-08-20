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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CardDetailScreen(
    onNavigateCardEdit: () -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateNext: (cardId: String, deckId: String) -> Unit,
    onNavigatePrev: (cardId: String, deckId: String) -> Unit,
    onDeleteCard: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cardDetailUiStateFlow: StateFlow<CardUiState> = viewModel.uiState
    val cardDetailUiState: State<CardUiState> = cardDetailUiStateFlow.collectAsState()
    val currentState: CardUiState = remember (cardDetailUiState.value) { cardDetailUiState.value }

    Scaffold(
        topBar = {
            LearnTopBar(
                title = "",
                canNavigateBack = true,
                navigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = { onNavigateCardEdit() }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
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
                        IconButton(onClick = { /*TODO*/ }) {
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
                        IconButton(onClick = { /*TODO*/ }) {
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


        
        val content = if (currentState.isFront) currentState.contentFront else currentState.contentBack

        CardDetailBody(
            content = content,
            modifier = modifier
                .padding(innerPadding)
        )

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