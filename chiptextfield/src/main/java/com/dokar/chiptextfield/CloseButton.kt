package com.dokar.chiptextfield

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T : Chip> CloseButton(
    state: ChipTextFieldState<T>,
    chip: T,
    modifier: Modifier = Modifier,
    backgroundColor: Color = if (MaterialTheme.colors.isLight) {
        Color.Black.copy(alpha = 0.3f)
    } else {
        Color.White.copy(alpha = 0.3f)
    },
    strokeColor: Color = Color.White,
    startPadding: Dp = 0.dp,
    endPadding: Dp = 6.dp
) {
    BasicCloseButton(
        state = state,
        chip = chip,
        modifier = modifier,
        backgroundColor = backgroundColor,
        strokeColor = strokeColor,
        startPadding = startPadding,
        endPadding = endPadding,
    )
}