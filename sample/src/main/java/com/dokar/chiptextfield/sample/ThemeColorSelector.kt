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

internal data class ChipFieldStyle(
    val textColor: Color,
    val borderColor: Color,
    val backgroundColor: Color,
    val cursorColor: Color
)

internal val CHIP_TEXT_FILED_STYLES = listOf(
    ChipFieldStyle(
        textColor = Color.Black,
        borderColor = Color.Black,
        backgroundColor = Color.Transparent,
        cursorColor = Color.Black
    ),
    ChipFieldStyle(
        textColor = Color.White,
        borderColor = Color(0xff94d2bd),
        backgroundColor = Color(0xff94d2bd),
        cursorColor = Color(0xff94d2bd)
    ),
    ChipFieldStyle(
        textColor = Color.White,
        borderColor = Color(0xffe85d04),
        backgroundColor = Color(0xffe85d04),
        cursorColor = Color(0xffe85d04)
    ),
    ChipFieldStyle(
        textColor = Color.White,
        borderColor = Color(0xff9fa0ff),
        backgroundColor = Color(0xff9fa0ff),
        cursorColor = Color(0xff9fa0ff)
    )
)

@Composable
internal fun ThemeColorSelector(
    selectedPosition: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for ((index, colors) in CHIP_TEXT_FILED_STYLES.withIndex()) {
            val chipColors = if (index == 0) {
                getDefaultChipFieldStyle()
            } else {
                colors
            }
            ColorItem(
                color = chipColors.borderColor,
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
internal fun getDefaultChipFieldStyle(): ChipFieldStyle {
    return CHIP_TEXT_FILED_STYLES[0].copy(
        textColor = MaterialTheme.colors.onBackground,
        borderColor = MaterialTheme.colors.onBackground,
        cursorColor = MaterialTheme.colors.primary
    )
}