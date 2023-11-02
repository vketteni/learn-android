package com.example.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LearnBottomAppBar(
    contentAlignment: Alignment = Alignment.Center,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    BottomAppBar(
        containerColor = Color.LightGray,
        tonalElevation = 1.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = contentAlignment
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = horizontalArrangement,
                content = actions
            )
        }
    }
}
