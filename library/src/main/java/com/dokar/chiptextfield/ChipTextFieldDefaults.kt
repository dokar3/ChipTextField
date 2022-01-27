package com.dokar.chiptextfield

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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

    @Composable
    fun indicatorStyle(
        focusedHeight: Dp = 2.dp,
        unfocusedHeight: Dp = 1.dp,
        readOnlyHeight: Dp = 0.dp,
        focusedColor: Color = MaterialTheme.colors.primary,
        unfocusedColor: Color = MaterialTheme.colors.onBackground.copy(alpha = 0.45f),
        readOnlyColor: Color = Color.Transparent,
    ): IndicatorStyle {
        return DefaultIndicatorStyle(
            focusedHeight = focusedHeight,
            unfocusedHeight = unfocusedHeight,
            readOnlyHeight = readOnlyHeight,
            focusedColor = focusedColor,
            unfocusedColor = unfocusedColor,
            readOnlyColor = readOnlyColor,
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
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Shape> {
        return rememberUpdatedState(shape)
    }

    @Composable
    override fun borderWidth(
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Dp> {
        return rememberUpdatedState(borderWidth)
    }

    @Composable
    override fun borderColor(
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        return rememberUpdatedState(borderColor)
    }

    @Composable
    override fun textColor(
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        return rememberUpdatedState(textColor)
    }

    @Composable
    override fun backgroundColor(
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

private class DefaultIndicatorStyle(
    private val focusedHeight: Dp,
    private val unfocusedHeight: Dp,
    private val readOnlyHeight: Dp,
    private val focusedColor: Color,
    private val unfocusedColor: Color,
    private val readOnlyColor: Color,
) : IndicatorStyle {
    @Composable
    override fun height(
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Dp> {
        val isFocused by interactionSource.collectIsFocusedAsState()
        return rememberUpdatedState(
            when {
                readOnly -> readOnlyHeight
                isFocused -> focusedHeight
                else -> unfocusedHeight
            }
        )
    }

    @Composable
    override fun color(
        readOnly: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val isFocused by interactionSource.collectIsFocusedAsState()
        return rememberUpdatedState(
            when {
                readOnly -> readOnlyColor
                isFocused -> focusedColor
                else -> unfocusedColor
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DefaultIndicatorStyle

        if (focusedHeight != other.focusedHeight) return false
        if (unfocusedHeight != other.unfocusedHeight) return false
        if (readOnlyHeight != other.readOnlyHeight) return false
        if (focusedColor != other.focusedColor) return false
        if (unfocusedColor != other.unfocusedColor) return false
        if (readOnlyColor != other.readOnlyColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = focusedHeight.hashCode()
        result = 31 * result + unfocusedHeight.hashCode()
        result = 31 * result + readOnlyHeight.hashCode()
        result = 31 * result + focusedColor.hashCode()
        result = 31 * result + unfocusedColor.hashCode()
        result = 31 * result + readOnlyColor.hashCode()
        return result
    }
}
