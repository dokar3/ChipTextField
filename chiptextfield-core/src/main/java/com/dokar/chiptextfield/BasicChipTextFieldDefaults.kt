package com.dokar.chiptextfield

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object BasicChipTextFieldDefaults {
    private const val disabledContentAlpha = 0.38f

    private val DefaultPlatformTextStyle = PlatformTextStyle(includeFontPadding = true)
    private val DefaultTextStyle = TextStyle.Default.copy(platformStyle = DefaultPlatformTextStyle)

    val textStyle: TextStyle = DefaultTextStyle

    @Composable
    fun chipStyle(
        shape: Shape = CircleShape,
        cursorColor: Color = Color.Black,
        focusedBorderWidth: Dp = 1.dp,
        unfocusedBorderWidth: Dp = 1.dp,
        disabledBorderWidth: Dp = 1.dp,
        focusedBorderColor: Color = Color.Black,
        unfocusedBorderColor: Color = focusedBorderColor,
        disabledBorderColor: Color = focusedBorderColor.copy(alpha = disabledContentAlpha),
        focusedTextColor: Color = Color.Black,
        unfocusedTextColor: Color = focusedTextColor,
        disabledTextColor: Color = focusedTextColor.copy(alpha = disabledContentAlpha),
        focusedBackgroundColor: Color = Color.Transparent,
        unfocusedBackgroundColor: Color = focusedBackgroundColor,
        disabledBackgroundColor: Color = focusedBackgroundColor,
    ): ChipStyle {
        return DefaultChipStyle(
            shape = shape,
            cursorColor = cursorColor,
            focusedBorderWidth = focusedBorderWidth,
            unfocusedBorderWidth = unfocusedBorderWidth,
            disabledBorderWidth = disabledBorderWidth,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            disabledBorderColor = disabledBorderColor,
            focusedTextColor = focusedTextColor,
            unfocusedTextColor = unfocusedTextColor,
            disabledTextColor = disabledTextColor,
            focusedBackgroundColor = focusedBackgroundColor,
            unfocusedBackgroundColor = unfocusedBackgroundColor,
            disabledBackgroundColor = disabledBackgroundColor,
        )
    }

    @Composable
    fun chipTextFieldColors(
        textColor: Color = Color.Black,
        disabledTextColor: Color = textColor.copy(alpha = disabledContentAlpha),
        errorTextColor: Color = Color.Red,
        cursorColor: Color = Color.Black,
        errorCursorColor: Color = Color.Red,
        backgroundColor: Color = Color.Transparent,
        disabledBackgroundColor: Color = Color.Transparent,
    ): ChipTextFieldColors = DefaultChipTextFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        errorTextColor = errorTextColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        backgroundColor = backgroundColor,
        disabledBackgroundColor = disabledBackgroundColor,
    )
}
