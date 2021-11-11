package com.dokar.chiptextfield.sample

import androidx.compose.ui.graphics.Color

internal data class ChipColors(
    val text: Color,
    val border: Color,
    val background: Color
)

internal val THEME_COLORS = listOf(
    ChipColors(text = Color.Black, border = Color.Black, background = Color.Transparent),
    ChipColors(text = Color.White, border = Color(0xff94d2bd), background = Color(0xff94d2bd)),
    ChipColors(text = Color.White, border = Color(0xffe85d04), background = Color(0xffe85d04)),
    ChipColors(text = Color.White, border = Color(0xff9fa0ff), background = Color(0xff9fa0ff))
)