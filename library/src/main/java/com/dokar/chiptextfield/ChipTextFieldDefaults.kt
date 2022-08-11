package com.dokar.chiptextfield

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ChipTextFieldDefaults {
    @Composable
    fun chipStyle(
        shape: Shape = CircleShape,
        borderWidth: Dp = 1.dp,
        borderColor: Color = Color.Black,
        textColor: Color = Color.Black,
        backgroundColor: Color = Color.Transparent,
    ): ChipStyle {
        return DefaultChipStyle(
            shape = shape,
            borderWidth = borderWidth,
            borderColor = borderColor,
            textColor = textColor,
            backgroundColor = backgroundColor,
        )
    }
}

private class DefaultChipStyle(
    private val shape: Shape,
    private val borderWidth: Dp,
    private val borderColor: Color,
    private val textColor: Color,
    private val backgroundColor: Color,
) : ChipStyle {
    @Composable
    override fun shape(
        enabled: Boolean,
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Shape> {
        return rememberUpdatedState(shape)
    }

    @Composable
    override fun borderWidth(
        enabled: Boolean,
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Dp> {
        return rememberUpdatedState(borderWidth)
    }

    @Composable
    override fun borderColor(
        enabled: Boolean,
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val alpha = if (enabled) 1f else ContentAlpha.disabled
        return rememberUpdatedState(borderColor.copy(alpha = alpha))
    }

    @Composable
    override fun textColor(
        enabled: Boolean,
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val alpha = if (enabled) 1f else ContentAlpha.disabled
        return rememberUpdatedState(textColor.copy(alpha = alpha))
    }

    @Composable
    override fun backgroundColor(
        enabled: Boolean,
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        return rememberUpdatedState(backgroundColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DefaultChipStyle

        if (shape != other.shape) return false
        if (borderWidth != other.borderWidth) return false
        if (borderColor != other.borderColor) return false
        if (textColor != other.textColor) return false
        if (backgroundColor != other.backgroundColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shape.hashCode()
        result = 31 * result + borderWidth.hashCode()
        result = 31 * result + borderColor.hashCode()
        result = 31 * result + textColor.hashCode()
        result = 31 * result + backgroundColor.hashCode()
        return result
    }
}
