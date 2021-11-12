package com.dokar.chiptextfield.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun ThemeColorSelector(
    selectedPosition: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for ((index, colors) in THEME_COLORS.withIndex()) {
            val chipColors = if (index == 0) {
                getDefaultChipColors()
            } else {
                colors
            }
            ColorItem(
                color = chipColors.border,
                isSelected = index == selectedPosition.value,
                modifier = Modifier.clickable { selectedPosition.value = index }
            )
        }
    }
}

@Composable
private fun ColorItem(
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) {
        color
    } else {
        Color.Transparent
    }
    Box(
        modifier = modifier.size(32.dp)
            .clip(CircleShape)
            .border(width = 2.dp, color = borderColor, shape = CircleShape)
            .padding(4.dp)
            .background(color = color, shape = CircleShape)
    )
}

@Composable
internal fun getDefaultChipColors(): ChipColors {
    return if (MaterialTheme.colors.isLight) {
        THEME_COLORS[0]
    } else {
        ChipColors(
            text = Color.White,
            border = Color.White,
            background = Color.Transparent
        )
    }
}