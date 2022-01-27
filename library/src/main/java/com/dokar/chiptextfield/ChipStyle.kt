package com.dokar.chiptextfield

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * Chip style
 */
interface ChipStyle {
    @Composable
    fun shape(readOnly: Boolean, interactionSource: InteractionSource): State<Shape>

    @Composable
    fun borderWidth(readOnly: Boolean, interactionSource: InteractionSource): State<Dp>

    @Composable
    fun borderColor(readOnly: Boolean, interactionSource: InteractionSource): State<Color>

    @Composable
    fun textColor(readOnly: Boolean, interactionSource: InteractionSource): State<Color>

    @Composable
    fun backgroundColor(readOnly: Boolean, interactionSource: InteractionSource): State<Color>
}