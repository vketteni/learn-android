package com.example.learn

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DeckOverviewTopBar(
    onRefresh: () -> Unit
) {
    LearnTopBar(
        title = R.string.decks_overview_title,
        canNavigateBack = false,
        actions = {
           MoreInfoMenu(onRefresh)
        }
    )
}

@Composable
private fun MoreInfoMenu(
    onRefresh: () -> Unit
) {
    TopAppBarDropdownMenu(
        iconContent = { Icon(Icons.Filled.MoreVert, stringResource(id = R.string.dropdown_menu))
        }
    ) {closeMenu ->
        DropdownMenuItem(onClick = { onRefresh(); closeMenu() }) {
            androidx.compose.material.Text(text = stringResource(id = R.string.decks_overview_refresh))
        }
    }
}

@Composable
private fun TopAppBarDropdownMenu(
    iconContent: @Composable () -> Unit,
    content: @Composable ColumnScope.(() -> Unit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box() {
        IconButton(onClick = { expanded = !expanded }) {
            iconContent()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            content { expanded = !expanded }
        }
    }
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnTopBar(
    @StringRes title: Int,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(stringResource(id = title), modifier.padding(horizontal = 8.dp)) },
            colors = TopAppBarDefaults.topAppBarColors(Color.LightGray),
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            },
            actions = actions
        )
    } else {
        TopAppBar(
            title = { Text(stringResource(id = title)) },
            modifier = modifier,
            actions = actions
        )
    }
}