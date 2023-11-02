package com.example.learn

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun LearnOutlinedTextField(
    @StringRes title: Int,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 8.dp)
        ) {
            Text(text = stringResource(id = title))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onValueChange(it) },
            placeholder = placeholder,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.DarkGray,
                focusedBorderColor = Color.DarkGray,
                selectionColors = TextSelectionColors(
                    handleColor = Color.LightGray,
                    backgroundColor = Color.Yellow
                )
            )
        )
    }
}
