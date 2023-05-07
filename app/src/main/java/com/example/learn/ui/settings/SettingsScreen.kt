package com.example.learn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SettingsScreen(
    onNavigateDeckOverviewScreen: () -> Unit,
){
    Scaffold() { paddingValues ->
        Row(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            Text(
                text = "Settings"
                , color = Color.Black,
                modifier = Modifier
                    .clickable(onClick = onNavigateDeckOverviewScreen)
            )

        }
    }
}