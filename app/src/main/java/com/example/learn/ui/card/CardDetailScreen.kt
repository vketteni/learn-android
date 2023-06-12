package com.example.learn.ui.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learn.LearnTopBar
import com.example.learn.R
import com.example.learn.ui.AppViewModelProvider
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CardDetailScreen(
    onNavigateCardEdit: () -> Unit,
    onNavigateUp: () -> Unit,
    onUpdateContent: (String) -> Unit,
    onSwitchSide: () -> Unit,
    onDeleteCard: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cardDetailUiStateFlow: StateFlow<CardUiState> = viewModel.uiState
    val cardDetailUiState: State<CardUiState> = cardDetailUiStateFlow.collectAsState()
    val currentState: CardUiState = remember (cardDetailUiState.value) { cardDetailUiState.value }
    val title = if (currentState.showFront) stringResource(R.string.card_detail_front_side_title) else stringResource(R.string.card_detail_back_side_title)


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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.switchSides() },
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp) // Add padding to the FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    ) { innerPadding ->


        
        val content = if (currentState.showFront) currentState.frontContent else currentState.backContent

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

@Composable
fun CardContent(
    content: String,
    modifier: Modifier = Modifier
) {

}