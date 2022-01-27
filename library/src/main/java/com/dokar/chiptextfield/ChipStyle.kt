package com.dokar.chiptextfield

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Chip style
 */
data class ChipStyle(
    val shape: Shape,
    val borderWidth: Dp,
    val borderColor: Color,
    val textColor: Color,
    val backgroundColor: Color,
) {
    companion object {
        val Default = ChipStyle(
            shape = CircleShape,
            borderWidth = 1.dp,
            borderColor = Color.Black,
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
        )
    }
}