package com.example.learn

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.learn.ui.navigation.LearnNavGraph

/**
 * Top level composable that represents application screens
 */
@Composable
fun LearnApp() {
    LearnNavGraph()
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
        TopAppBar(title = { Text(stringResource(id = title), modifier.padding(horizontal = 8.dp)) },
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
            modifier = modifier
        )
    }
}